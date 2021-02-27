package com.github.mtlsclient.interceptor;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

import static java.lang.String.format;
import static java.lang.System.out;

public class RequestLogHttpRequestInterceptor implements HttpRequestInterceptor {
    @Override
    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
        final long start = System.currentTimeMillis();
        String msg = "REQUEST requestLine=%s, protocolVersion=%s";
        out.println(format(msg, request.getRequestLine(), request.getProtocolVersion()));
    }
}
