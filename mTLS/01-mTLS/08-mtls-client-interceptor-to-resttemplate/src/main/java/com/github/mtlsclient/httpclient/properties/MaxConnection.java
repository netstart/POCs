package com.github.mtlsclient.httpclient.properties;

public class MaxConnection {
    //Max connection per connection pool
    public int maxConnTotal = 1000;

    // Max connection por route (route formed by protocol:host:port)
    public int maxConnPerRoute = 100;
}
