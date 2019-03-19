package hum.pool;

import java.nio.channels.SocketChannel;

/**
 * @author hum
 */
public interface Worker {
    public void registerNewChannelTask(SocketChannel channel);

}
