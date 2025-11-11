package com.demo;

import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.List;

@SpringBootTest()
public class NacosClientTest {

    @Autowired
    public DiscoveryClient discoveryClient;

    @Autowired
    NacosServiceDiscovery nacosServiceDiscovery;


    @Test
    public void discoverClientTest(){
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            System.out.println("discoverClientTest service:  " + service);
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            for (ServiceInstance instance : instances) {
                System.out.println("\t地址 "+ instance.getHost()+" : "+ instance.getPort());
            }
        }
    }

    @Test
    public void discoverClientTest2() throws Exception{
        List<String> services = nacosServiceDiscovery.getServices();
        for (String service : services) {
            System.out.println("discoverClientTest2 service:  " + service);
            List<ServiceInstance> instances = nacosServiceDiscovery.getInstances(service);
            for (ServiceInstance instance : instances) {
                System.out.println("\t地址 "+ instance.getHost()+" : "+ instance.getPort());
            }
        }
    }



}
