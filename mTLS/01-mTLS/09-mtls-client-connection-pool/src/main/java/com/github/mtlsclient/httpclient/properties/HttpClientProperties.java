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
    public Timeout timeout = new Timeout();
    public ConnectionTimeToLive connectionTimeToLive = new ConnectionTimeToLive();

    // Sets duration after which persistent connections needs to be re-validated before leasing
    public int validateAfterInactivityInMillis = 9000;

    public List<HttpRequestInterceptor> interceptors = new ArrayList<>();
}
