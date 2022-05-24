package com.test.serviceconsumer.service.Impl;

import com.test.serviceconsumer.entity.Order;
import com.test.serviceconsumer.entity.Product;
import com.test.serviceconsumer.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;        //远程调用服务方式一：元数据远程调用服务
    @Autowired
    private LoadBalancerClient loadBalancerClient; //远程调用服务方式二：Ribbon负载均衡器

    @Override
    public List<Order> getList() {
        List<Order> list = new ArrayList<>();
        Order order = new Order();
        order.setId(1);
        order.setOrderAddress("中国");
        order.setOrderNo("1231231");
        order.setTotalPrice(5.32);
        order.setListProduct(selectProductListByLoadBalancerAnnctation());
        list.add(order);
        return list;
    }

    //远程调用服务方式一：元数据远程调用服务
    private List<Product> selectProductListByDiscoveryClient(){
        StringBuffer sb = null;

        //获取服务列表
        List<String> serviceIds = discoveryClient.getServices();
        if(CollectionUtils.isEmpty(serviceIds)){
            return null;
        }

        //根据服务名称获取服务
        List<ServiceInstance> serviceInstance = discoveryClient.getInstances("service-pricider");
        if(CollectionUtils.isEmpty(serviceInstance)){
            return null;
        }
        ServiceInstance si = serviceInstance.get(0);
        sb = new StringBuffer();
        sb.append("http://" + si.getHost() + ":" + si.getPort() + "api/getList");
        //ResponseEntity封装了返回数据
        ResponseEntity<List<Product>> responseEntity = restTemplate.exchange(
                sb.toString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {});
        return responseEntity.getBody();
    }

    //远程调用服务方式二：Ribbon负载均衡器
    private List<Product> selectProductListByLoadBalancerClient(){
        StringBuffer sb = null;
        //根据服务名称获取服务
        ServiceInstance si = loadBalancerClient.choose("service-pricider");
        if(null == si)
            return null;
        sb = new StringBuffer();
        sb.append("http://" + si.getHost() + ":" + si.getPort() + "api/getList");
        //ResponseEntity封装了返回数据
        ResponseEntity<List<Product>> responseEntity = restTemplate.exchange(
                sb.toString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {});
        return responseEntity.getBody();
    }

    //远程调用服务方式三：Ribbon负载均衡器注解
    private List<Product> selectProductListByLoadBalancerAnnctation(){
        //ResponseEntity封装了返回数据
        ResponseEntity<List<Product>> responseEntity = restTemplate.exchange(
                "http://service-pricider/api/getList",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {});
        return responseEntity.getBody();
    }
}
