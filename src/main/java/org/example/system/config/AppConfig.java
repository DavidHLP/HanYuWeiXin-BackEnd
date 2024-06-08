package org.example.system.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableRedisRepositories
@EnableWebMvc
@ComponentScan(basePackages = "org.example.system.config")
public class AppConfig {
}
