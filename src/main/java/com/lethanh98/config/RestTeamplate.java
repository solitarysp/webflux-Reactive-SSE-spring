package com.lethanh98.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Configuration
public class RestTeamplate {

    @Autowired
    InterceptorDefaultRest interceptorDefaultRest;

    @Bean
    @Primary
    public RestTemplate restTemplateDefault() {

        SimpleClientHttpRequestFactory httpRequestFactory = new SimpleClientHttpRequestFactory();
        httpRequestFactory.setConnectTimeout(6666);
        httpRequestFactory.setReadTimeout(6666);
        //httpRequestFactory.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.60.135.36", 8800)));
        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(httpRequestFactory));
        restTemplate.setInterceptors(Collections.singletonList(interceptorDefaultRest));
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }

    @Bean("restTemplateCheck")
    public RestTemplate restTemplateCheck() {

        SimpleClientHttpRequestFactory httpRequestFactory = new SimpleClientHttpRequestFactory();
        httpRequestFactory.setConnectTimeout(2666);
        httpRequestFactory.setReadTimeout(2666);
        //httpRequestFactory.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.60.135.36", 8800)));
        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(httpRequestFactory));
        restTemplate.setInterceptors(Collections.singletonList(interceptorDefaultRest));
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }


}

@Slf4j
@Component
class InterceptorDefaultRest implements ClientHttpRequestInterceptor {

    @Autowired
    UtilsRestTemplate utilsRestTemplate;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        try {
            HttpHeaders headers = request.getHeaders();
            utilsRestTemplate.addHeader(headers, HeaderConstant.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            utilsRestTemplate.addHeader(headers, HeaderConstant.ACCEPT_LANGUAGE, "vi");

        } catch (Exception e) {
            log.error("Interceptor error : ", e);
        }
        ClientHttpResponse httpResponse = execution.execute(request, body);
        return httpResponse;
    }
}
