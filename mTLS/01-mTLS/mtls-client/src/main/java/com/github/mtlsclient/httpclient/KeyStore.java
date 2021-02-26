package com.github.mtlsclient.httpclient;

import java.io.File;

public class KeyStore {

  private File keyStore;
  private char[] keyStorePassword;
  private char[] keyPassword;

  public KeyStore(File keyStore, char[] keyStorePassword, char[] keyPassword) {
    this.keyStore = keyStore;
    this.keyStorePassword = keyStorePassword;
    this.keyPassword = keyPassword;
  }

  public File getKeyStore() {
    return keyStore;
  }

  public char[] getKeyStorePassword() {
    return keyStorePassword;
  }

  public char[] getKeyPassword() {
    return keyPassword;
  }

}
