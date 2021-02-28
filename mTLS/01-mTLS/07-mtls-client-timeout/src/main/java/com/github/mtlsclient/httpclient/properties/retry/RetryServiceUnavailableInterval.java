package com.github.mtlsclient.httpclient.properties.retry;

/**
 * A quantidade de retentativas (no caso 3) e o intervalo entre as retentativas (no caso 1 milissegundo)
 */
public class RetryServiceUnavailableInterval {
    public int maxRetries = 3;
    public int retryInterval = 1000*30;
}
