package me.dongqinglin.pedro_coder;

import me.dongqinglin.pedro_coder.AFilter.GatewayFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;


@EnableEurekaClient
@SpringBootApplication
public class PedroCoderApplication {

    @Bean
    public GatewayFilter gatewayFilter(){
        return new GatewayFilter();
    }

    public static void main(String[] args) {
        SpringApplication.run(PedroCoderApplication.class, args);
    }

}
