package com.github.mtlsclient.httpclient;

import com.github.mtlsclient.httpclient.properties.HttpClientProperties;
import org.apache.http.HttpRequestInterceptor;
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

        HttpClientBuilder builder = HttpClients.custom();
        disableGzip(builder, properties);
        addMaxConnection(builder, properties);
        addSoSSLSocketFactory(builder, properties);
        addInterceptors(builder, properties);

        return builder.build();
    }

    private HttpClientBuilder addSoSSLSocketFactory(HttpClientBuilder builder, HttpClientProperties properties)
            throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
        return builder.setSSLSocketFactory(
                SSLConnectionSocketFactoryInstance.getInstance(properties.trustStore, properties.keyStore)
        );
    }

    private void addInterceptors(HttpClientBuilder builder, HttpClientProperties properties) {
        if (properties.interceptors != null) {
            for (HttpRequestInterceptor interceptor : properties.interceptors)
                builder.addInterceptorLast(interceptor);
        }
    }

    private void addMaxConnection(HttpClientBuilder builder, HttpClientProperties properties) {
        builder.setMaxConnTotal(properties.maxConnection.maxConnTotal);
        builder.setMaxConnPerRoute(properties.maxConnection.maxConnPerRoute);
    }

    private void disableGzip(HttpClientBuilder builder, HttpClientProperties properties){
        if(properties.disableGzip) {
            builder.disableContentCompression();
        }
    }
}

