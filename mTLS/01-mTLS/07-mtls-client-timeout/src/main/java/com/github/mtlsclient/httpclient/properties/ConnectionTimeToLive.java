package com.github.mtlsclient.httpclient.properties;

import java.util.concurrent.TimeUnit;

/**
 * Se você tem uma infraestrutura dinâmica, estará sujeito a mudanças de IPs de hosts, por exemplo,
 * em um blue/green deployment, onde a infraestrutura é imutável e tende a ser sempre substituída por uma nova,
 * com novos IPs. Neste caso, é necessário ter um tempo máximo de vida para suas conexões,
 * senão sua infraestrutura anterior (blue) nunca vai poder ser desligada e sua infraestrutura atual (green) vai
 * receber requests apenas das novas conexões que forem abertas sob demanda.
 *
 * Desta forma, garantimos que nenhuma conexão ficará aberta por mais de 30 minutos,
 * então em uma situação de blue/green temos um tempo bem definido para saber que determinada
 * infraestrutura não está mais sendo usada.
 */
public class ConnectionTimeToLive {
    // Tempo que a conexão é mantida viva.
    public int time = 30;
    public TimeUnit timeUnit = TimeUnit.MINUTES;
}
