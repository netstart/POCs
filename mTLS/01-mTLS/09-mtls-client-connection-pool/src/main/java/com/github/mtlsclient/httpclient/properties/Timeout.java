package com.github.mtlsclient.httpclient.properties;

/**
 * Todos esses timeouts são definidos em milissegundos.
 *
 * Não temos como configurar connectionRequestTimeout no request.
 * O comportamento da lib ao chegar ao limite de sockets é
 * enfileirar o request atual, que será executado assim que um socket estiver livre para isso.
 */
public class Timeout {
    /**
     * O tempo limite para aguardar por uma conexão do pool de conexões
     */
    public int connectionRequestTimeout = 1000;

    /**
     *   O tempo limite para estabelecer uma conexão com o host desejado
     */

    public int connectionTimeout = 5000;

    /**
     * O tempo máximo de espera para finalizar a comunicação com o host de destino
     */
    public int socketTimeout = 1000;
}
