package com.github.mtlsclient.httpclient.properties;

import org.apache.http.HttpRequestInterceptor;

import java.util.ArrayList;
import java.util.List;

public class HttpClientProperties {

    public boolean disableGzip;

    public KeyStore keyStore;
    public TrustStore trustStore;

    public MaxConnection maxConnection = new MaxConnection();
    public ConnectionTimeToLive connectionTimeToLive = new ConnectionTimeToLive();

    public List<HttpRequestInterceptor> interceptors = new ArrayList<>();
}
