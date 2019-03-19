package hum.pool;

import java.nio.channels.ServerSocketChannel;

/**
 * @author hum
 */
public interface Boss {
    public void registerAcceptChannelTask(ServerSocketChannel serverChannel);
}
