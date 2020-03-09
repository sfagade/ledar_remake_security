package org.tsp.projects.ledar.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").exposedHeaders("Version","0.0.1");
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        int timeout = 120000;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        clientHttpRequestFactory.setReadTimeout(timeout);
        clientHttpRequestFactory.setConnectionRequestTimeout(timeout);
        return clientHttpRequestFactory;
    }
}
