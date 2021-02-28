package com.github.mtlsclient.config;

import com.github.mtlsclient.httpclient.HttpClientFactory;
import com.github.mtlsclient.httpclient.properties.*;
import com.github.mtlsclient.httpclient.properties.retry.RetryServiceUnavailableInterval;
import com.github.mtlsclient.interceptor.RequestLogHttpRequestInterceptor;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.*;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

import static java.lang.String.format;
import static java.lang.System.out;

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

        //        restTemplate.setInterceptors(asList(new BasicAuthorizationInterceptor("user", "password")));
        restTemplate.setInterceptors(List.of(logInterceptor()));

        return restTemplate;
    }

    public ClientHttpRequestInterceptor logInterceptor() {
        return (request, body, execution) -> {
            ClientHttpResponse response = null;
            final long start = System.currentTimeMillis();
            try {
                return response = execution.execute(request, body);
            } finally {
                final String requestMsg = "[RestTemplateConfiguration] REQUEST method=%s uri=%s, params=%s";
                out.println(format(requestMsg, request.getMethod(), request.getURI(), request.getURI().getQuery()));

                if (response != null) {
                    final String responseMsg = "[RestTemplateConfiguration] RESPONSE status=%s, time=%s";
                    out.println(format(responseMsg, response.getRawStatusCode(), System.currentTimeMillis() - start));
                }
            }
        };
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() throws Exception {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }

    private HttpClient httpClient() throws Exception {
        HttpClientProperties properties = new HttpClientProperties();
        properties.disableGzip = true;

        properties.maxRetry = 5;
        properties.retryServiceUnavailableInterval = new RetryServiceUnavailableInterval();

        properties.timeout = new Timeout();
        properties.maxConnection = new MaxConnection();
        properties.connectionTimeToLive = new ConnectionTimeToLive();

        properties.trustStore = new TrustStore(trustStore.getFile(), trustStorePassword.toCharArray());
        properties.keyStore = new KeyStore(keyStore.getFile(), keyStorePassword.toCharArray(), keyPassword.toCharArray());

        properties.interceptors = List.of(new RequestLogHttpRequestInterceptor());

        return new HttpClientFactory().createHttpClient(properties);
    }
}
