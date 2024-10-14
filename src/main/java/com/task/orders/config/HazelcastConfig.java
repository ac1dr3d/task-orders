package com.task.orders.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MapConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfig {

    @Bean(name = "customHazelcastConfig")
    public Config hazelcastConfig() {
        Config config = new Config();
        MapConfig mapConfig = new MapConfig("orderEntityCache");
        mapConfig.setTimeToLiveSeconds(30);
        config.addMapConfig(mapConfig);
        NetworkConfig networkConfig = config.getNetworkConfig();
        JoinConfig joinConfig = networkConfig.getJoin();
        joinConfig.getMulticastConfig().setEnabled(false);
        joinConfig.getTcpIpConfig().setEnabled(true).addMember("127.0.0.1");
        return config;
    }
}