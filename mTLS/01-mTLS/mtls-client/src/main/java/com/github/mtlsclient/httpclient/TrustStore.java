package com.github.mtlsclient.httpclient;

import java.io.File;

public class TrustStore {

  private File trustStore;
  private char[] trustStorePassword;

  public TrustStore(File trustStore, char[] trustStorePassword) {
    this.trustStore = trustStore;
    this.trustStorePassword = trustStorePassword;
  }

  public File getTrustStore() {
    return trustStore;
  }

  public char[] getTrustStorePassword() {
    return trustStorePassword;
  }

}
