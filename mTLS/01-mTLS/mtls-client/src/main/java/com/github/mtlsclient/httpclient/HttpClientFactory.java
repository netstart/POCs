package com.github.mtlsclient;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;

public class HttpClientFactory {

  public HttpClient createHttpClient(TrustStore trustStore, KeyStore keyStore)
    throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException,
    CertificateException, IOException {

 // @formatter:off
    return HttpClients
      .custom()
      .setSSLSocketFactory(sslConnectionSocketFactory(createSSLContext(trustStore,  keyStore)))
      .build();
    // @formatter:on
  }

  public CloseableHttpClient createCloseableHttpClient(TrustStore trustStore, KeyStore keyStore)
    throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException,
    CertificateException, IOException {

 // @formatter:off
    return HttpClients
      .custom()
      .setSSLSocketFactory(sslConnectionSocketFactory(createSSLContext(trustStore,  keyStore)))
      .build();
    // @formatter:on
  }

  public SSLContext createSSLContext(TrustStore trustStore, KeyStore keyStore)
    throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException,
    CertificateException, IOException {

    // @formatter:off
    return SSLContexts
      .custom()
      .loadTrustMaterial(trustStore.getTrustStore(), trustStore.getTrustStorePassword())
      .loadKeyMaterial(keyStore.getKeyStore(), keyStore.getKeyStorePassword(), keyStore.getKeyPassword())
      .build();
    // @formatter:on

  }

  public SSLConnectionSocketFactory sslConnectionSocketFactory(SSLContext sslContext) {
    return new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
  }

}
