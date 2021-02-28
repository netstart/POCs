package com.github.mtlsclient.httpclient.properties;

import com.github.mtlsclient.httpclient.properties.retry.RetryServiceUnavailableInterval;
import org.apache.http.HttpRequestInterceptor;

import java.util.ArrayList;
import java.util.List;

public class HttpClientProperties {

    public boolean disableGzip;

    public int maxRetry = 3;
    public RetryServiceUnavailableInterval retryServiceUnavailableInterval = new RetryServiceUnavailableInterval();

    public KeyStore keyStore;
    public TrustStore trustStore;

    public MaxConnection maxConnection = new MaxConnection();
    public ConnectionTimeToLive connectionTimeToLive = new ConnectionTimeToLive();

    public List<HttpRequestInterceptor> interceptors = new ArrayList<>();
}
