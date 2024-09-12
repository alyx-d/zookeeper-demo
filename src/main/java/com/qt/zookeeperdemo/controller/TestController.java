package com.qt.zookeeperdemo.controller;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private CuratorFramework client;

    @Autowired
    private Environment environment;

    @Value("${name}")
    private String name;

    @PostMapping("/updateName")
    public String updateName(String name) throws Exception {
        client.setData()
                .forPath("/config/application/name", name.getBytes(StandardCharsets.UTF_8));
        return name;
    }

    @GetMapping("/getNameFromZk")
    public String getNameFromZk() {

        return environment.getProperty("name");
    }

    @GetMapping("/serviceUri")
    public List<String> serviceUri() {
        var uris = new ArrayList<String>();
        var instances = discoveryClient.getInstances("zookeeper-demo");
        instances.forEach(instance -> {
            uris.add(instance.getUri().toString());
        });
        return uris;
    }
}
