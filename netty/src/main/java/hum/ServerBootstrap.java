package hum;

import hum.pool.Boss;
import hum.pool.SelectorRunnablePool;

import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;

/**
 * @author hum
 */
public class ServerBootstrap {

    private SelectorRunnablePool selectorRunnablePool;

    public ServerBootstrap(SelectorRunnablePool selectorRunnablePool) {
        this.selectorRunnablePool = selectorRunnablePool;
    }


    public void bind(final SocketAddress localAddress) {
        try {
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind(localAddress);

            Boss nextBoss = selectorRunnablePool.nextBoss();
            nextBoss.registerAcceptChannelTask(serverChannel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

