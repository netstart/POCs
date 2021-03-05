package com.github.mtlsclient.httpclient;

import com.github.mtlsclient.httpclient.properties.HttpClientProperties;
import org.apache.http.config.Registry;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import static java.lang.String.format;
import static java.lang.System.out;

public class PoolingHttpClientFactory {

    public PoolingHttpClientConnectionManager poolingConnectionManager(HttpClientProperties properties) throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, IOException {

        Registry<ConnectionSocketFactory> sslConnectionSocket = new SSLConnectionSocketFactoryInstance().getRegistryConnectionSocketFactory(properties.trustStore, properties.keyStore);
        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager(sslConnectionSocket);
        poolingConnectionManager.setMaxTotal(properties.maxConnection.maxConnTotal);
        poolingConnectionManager.setDefaultMaxPerRoute(properties.maxConnection.maxConnPerRoute);
        poolingConnectionManager.setValidateAfterInactivity(properties.validateAfterInactivityInMillis);


        String msg = "Connection pool instantiated with max connections = %s, " +
                "max per route connections = %s," +
                "validate after inactivity in millis = %s";
        out.println(format(msg, properties.maxConnection.maxConnTotal, properties.maxConnection.maxConnPerRoute,
                properties.validateAfterInactivityInMillis));
        return poolingConnectionManager;
    }
}
