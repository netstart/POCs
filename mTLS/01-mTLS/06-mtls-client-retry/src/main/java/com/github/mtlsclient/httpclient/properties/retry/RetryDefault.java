package com.github.mtlsclient.httpclient.properties.retry;

import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * Em certos casos, retentar um request pode poupar o usuário de tomar um erro na tela, pode poupar sua aplicação de falhar,
 * pode ser… melhor. Por exemplo, sua aplicação tem alguma dependência instável
 * (um cluster que constantemente tem máquinas respondendo normalmente e outras falhando,
 * máquinas sendo reiniciadas, falhas de rede, etc), nesse caso fazer uma retentativa pode garantir que
 * sua aplicação consiga se comunicar ao invés de falhar (ao custo de demorar mais para responder).
 * <p>
 * <p>
 * Aqui temos um tradeoff a ser feito: O que é melhor para sua aplicação? Falhar rapidamente ou responder com sucesso
 * porém mais lentamente? Quantas vezes uma retentativa vai ser executada? Devemos diminuir o timeout e confiar que uma
 * retentativa será respondida mais rápido do que a primeira tentativa? Devemos aumentar o timeout e não fazer nenhuma
 * retentativa?
 * <p>
 * O código configura uma quantidade de 3 retentativas, também poderíamos implementar alguma lógica relacionada
 * com a IOException que ocorreu ou com o contexto do request.
 */
public class RetryDefault implements HttpRequestRetryHandler {
    private int maxRetry = 3;

    public RetryDefault(int maxRetry){
        this.maxRetry = maxRetry;
    }

    @Override
    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
        return executionCount <= maxRetry;
    }

}
