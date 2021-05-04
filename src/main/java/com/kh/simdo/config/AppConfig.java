package com.kh.simdo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5000);
        requestFactory.setReadTimeout(5000);

        return new RestTemplate(requestFactory);
    }

    //여기원래 멀티파트리졸버 있었는데 괜히 빈등록해서 안됐떤거. 굳이 안해도 잘된다. 기본설정을 왠만하면 쓰자.

    // 원래 App에 있었는데 로직상 시큐리티에 있는게 맞다. -> 무한참조때매 다시 옮긴다
    @Bean
    public BCryptPasswordEncoder passwordEndcoder() {
        return new BCryptPasswordEncoder();
    }
}
