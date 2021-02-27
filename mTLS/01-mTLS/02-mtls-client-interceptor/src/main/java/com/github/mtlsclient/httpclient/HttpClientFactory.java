package com.github.mtlsclient.httpclient;

import com.github.mtlsclient.httpclient.properties.HttpClientProperties;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class HttpClientFactory {

    public HttpClient createHttpClient(HttpClientProperties properties)
            throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, IOException {
        return this.createCloseableHttpClient(properties);
    }

    public CloseableHttpClient createCloseableHttpClient(HttpClientProperties properties)
            throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException,
            CertificateException, IOException {

        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        addInterceptors(httpClientBuilder, properties);
        addSoSSLSocketFactory(httpClientBuilder, properties);

        return httpClientBuilder.build();
    }

    private HttpClientBuilder addSoSSLSocketFactory(HttpClientBuilder httpClientBuilder, HttpClientProperties properties)
            throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
        return httpClientBuilder.setSSLSocketFactory(
                SSLConnectionSocketFactoryInstance.getInstance(properties.trustStore, properties.keyStore)
        );
    }

    private void addInterceptors(HttpClientBuilder httpClientBuilder, HttpClientProperties properties) {
        if (properties.interceptors != null) {
            properties.interceptors.stream()
                    .map(interceptor -> httpClientBuilder.addInterceptorLast(interceptor));
        }
    }
}
