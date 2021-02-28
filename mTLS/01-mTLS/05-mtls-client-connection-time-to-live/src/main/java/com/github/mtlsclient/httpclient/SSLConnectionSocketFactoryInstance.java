package com.github.mtlsclient.httpclient;

import com.github.mtlsclient.httpclient.properties.KeyStore;
import com.github.mtlsclient.httpclient.properties.TrustStore;
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

    private SSLConnectionSocketFactoryInstance() {
    }

    public static SSLConnectionSocketFactory getInstance(TrustStore trustStore, KeyStore keyStore) throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, IOException {
        SSLConnectionSocketFactoryInstance me = new SSLConnectionSocketFactoryInstance();
        return me.sslConnectionSocketFactory(me.createSSLContext(trustStore, keyStore));
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
