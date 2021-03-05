package com.github.mtlsclient.httpclient;

import com.github.mtlsclient.httpclient.properties.KeyStore;
import com.github.mtlsclient.httpclient.properties.TrustStore;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class SSLConnectionSocketFactoryInstance {

    public Registry<ConnectionSocketFactory> getRegistryConnectionSocketFactory(TrustStore trustStore, KeyStore keyStore) throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, IOException {
        SSLConnectionSocketFactory sslsf = getSSlConnectionSocketFactory(trustStore, keyStore);
        return RegistryBuilder
                .<ConnectionSocketFactory>create()
                .register("https", sslsf)
                .register("http", new PlainConnectionSocketFactory())
                .build();
    }

    public SSLConnectionSocketFactory getSSlConnectionSocketFactory(TrustStore trustStore, KeyStore keyStore) throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, IOException {
        return sslConnectionSocketFactory(createSSLContext(trustStore, keyStore));
    }

    protected SSLConnectionSocketFactory sslConnectionSocketFactory(SSLContext sslContext) {
        // NoopHostnameVerifier essentially turns hostname verification off as otherwise following error
        // is thrown: java.security.cert.CertificateException: No name matching localhost found
        return new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
    }

    protected SSLContext createSSLContext(TrustStore trustStore, KeyStore keyStore)
            throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException,
            CertificateException, IOException {
        // Load our keystore and truststore containing certificates that we trust.
        // @formatter:off
        return SSLContexts.custom()
                .loadTrustMaterial(trustStore.getTrustStore(), trustStore.getTrustStorePassword())
                .loadKeyMaterial(keyStore.getKeyStore(), keyStore.getKeyStorePassword(), keyStore.getKeyPassword())
                .build();
        // @formatter:on

    }


}
