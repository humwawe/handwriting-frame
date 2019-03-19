package hum;

import hum.pool.Boss;
import hum.pool.SelectorRunnablePool;
import hum.pool.Worker;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;

/**
 * @author hum
 */
public class ServerBoss extends AbstractSelector implements Boss {

    public ServerBoss(Executor executor, String threadName, SelectorRunnablePool selectorRunnablePool) {
        super(executor, threadName, selectorRunnablePool);
    }

    @Override
    protected void process(Selector selector) throws IOException {
        Set<SelectionKey> selectedKeys = selector.selectedKeys();
        if (selectedKeys.isEmpty()) {
            return;
        }

        for (Iterator<SelectionKey> i = selectedKeys.iterator(); i.hasNext(); ) {
            SelectionKey key = i.next();
            i.remove();
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            SocketChannel channel = server.accept();
            channel.configureBlocking(false);
            Worker nextWorker = getSelectorRunnablePool().nextWorker();
            nextWorker.registerNewChannelTask(channel);

            System.out.println("新客户端链接");
        }
    }


    @Override
    public void registerAcceptChannelTask(final ServerSocketChannel serverChannel) {
        final Selector selector = this.selector;
        registerTask(() -> {
            try {
                serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            } catch (ClosedChannelException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected int select(Selector selector) throws IOException {
        return selector.select();
    }

}

