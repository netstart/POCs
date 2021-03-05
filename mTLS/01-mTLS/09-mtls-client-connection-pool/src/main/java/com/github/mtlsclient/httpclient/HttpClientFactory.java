package com.github.mtlsclient.httpclient;

import com.github.mtlsclient.httpclient.properties.HttpClientProperties;
import com.github.mtlsclient.httpclient.properties.retry.RetryDefault;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultServiceUnavailableRetryStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

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

        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientFactory().poolingConnectionManager(properties);
        builder.setConnectionManager(poolingConnectionManager);

// Defined on PoolingHttpClientFactory
// addMaxConnection(builder, properties);

// Defined on PoolingHttpClientFactory
// addSSLSocketFactory(builder, properties);

        disableGzip(builder, properties);
        addInterceptors(builder, properties);
        addConnectionTimeToLive(builder, properties);
        addRetry(builder, properties);
        addServiceUnavailableRetryStrategy(builder, properties);
        addTimeout(builder, properties);
        return builder.build();
    }


    private HttpClientBuilder addSSLSocketFactory(HttpClientBuilder builder, HttpClientProperties properties)
            throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
        return builder.setSSLSocketFactory(
                new SSLConnectionSocketFactoryInstance()
                        .getSSlConnectionSocketFactory(properties.trustStore, properties.keyStore)
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

    private void disableGzip(HttpClientBuilder builder, HttpClientProperties properties) {
        if (properties.disableGzip) {
            builder.disableContentCompression();
        }
    }

    private void addConnectionTimeToLive(HttpClientBuilder builder, HttpClientProperties properties) {
        // NoopHostnameVerifier essentially turns hostname verification off as otherwise following error
        // is thrown: java.security.cert.CertificateException: No name matching localhost found
        builder.setConnectionTimeToLive(properties.connectionTimeToLive.time, properties.connectionTimeToLive.timeUnit);
    }

    private void addRetry(HttpClientBuilder builder, HttpClientProperties properties) {
        if (properties.maxRetry > 0) {
            final RetryDefault retryDefault = new RetryDefault(properties.maxRetry);
            builder.setRetryHandler(retryDefault);
        }
    }

    private void addServiceUnavailableRetryStrategy(HttpClientBuilder builder, HttpClientProperties properties) {
        final int maxRetries = properties.retryServiceUnavailableInterval.maxRetries;
        final int retryInterval = properties.retryServiceUnavailableInterval.retryInterval;

        // Internamente ela verifica se recebeu um status code 503 e decide até quando retentar com base nos argumentos do construtor
        final DefaultServiceUnavailableRetryStrategy retryStrategy = new DefaultServiceUnavailableRetryStrategy(maxRetries, retryInterval);
        builder.setServiceUnavailableRetryStrategy(retryStrategy);
    }

    private void addTimeout(HttpClientBuilder builder, HttpClientProperties properties) {
        final RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(properties.timeout.connectionRequestTimeout)
                .setConnectTimeout(properties.timeout.connectionTimeout)
                .setSocketTimeout(properties.timeout.socketTimeout)
                .build();

        builder.setDefaultRequestConfig(requestConfig);
    }
}

