package com.github.mtlsclient.config;

import com.github.mtlsclient.httpclient.HttpClientFactory;
import com.github.mtlsclient.httpclient.properties.*;
import com.github.mtlsclient.interceptor.RequestLogHttpRequestInterceptor;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class RestTemplateConfiguration {

    @Value("${server.ssl.trust-store-password}")
    private String trustStorePassword;

    @Value("${server.ssl.trust-store}")
    private Resource trustStore;

    @Value("${server.ssl.key-store-password}")
    private String keyStorePassword;

    @Value("${server.ssl.key-password}")
    private String keyPassword;

    @Value("${server.ssl.key-store}")
    private Resource keyStore;

    @Bean
    public RestTemplate restTemplate() throws Exception {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
        restTemplate.setErrorHandler(
                new DefaultResponseErrorHandler() {
                    @Override
                    protected boolean hasError(HttpStatus statusCode) {
                        return false;
                    }
                });

        return restTemplate;
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() throws Exception {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }

    private HttpClient httpClient() throws Exception {
        HttpClientProperties properties = new HttpClientProperties();
        properties.disableGzip = true;

        properties.maxConnection = new MaxConnection();
        properties.connectionTimeToLive = new ConnectionTimeToLive();

        properties.trustStore = new TrustStore(trustStore.getFile(), trustStorePassword.toCharArray());
        properties.keyStore = new KeyStore(keyStore.getFile(), keyStorePassword.toCharArray(), keyPassword.toCharArray());

        properties.interceptors = List.of(new RequestLogHttpRequestInterceptor());

        return new HttpClientFactory().createHttpClient(properties);
    }
}
