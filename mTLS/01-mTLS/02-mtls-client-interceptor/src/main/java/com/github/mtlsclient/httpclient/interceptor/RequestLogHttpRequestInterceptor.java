package com.github.mtlsclient.httpclient.interceptor;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class RequestLogHttpRequestInterceptor implements HttpRequestInterceptor {
    Logger log = LoggerFactory.getLogger(RequestLogHttpRequestInterceptor.class);

    @Override
    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
        final long start = System.currentTimeMillis();
        log.info("REQUEST requestLine={}, protocolVersion={}", request.getRequestLine(), request.getProtocolVersion()); // <--
    }
}
