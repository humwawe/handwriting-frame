package hum;

import hum.pool.SelectorRunnablePool;
import hum.pool.Worker;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;

/**
 * @author hum
 */
public class ServerWorker extends AbstractSelector implements Worker {

    public ServerWorker(Executor executor, String threadName, SelectorRunnablePool selectorRunnablePool) {
        super(executor, threadName, selectorRunnablePool);
    }

    @Override
    protected void process(Selector selector) throws IOException {
        Set<SelectionKey> selectedKeys = selector.selectedKeys();
        if (selectedKeys.isEmpty()) {
            return;
        }
        Iterator<SelectionKey> ite = this.selector.selectedKeys().iterator();
        while (ite.hasNext()) {
            SelectionKey key = (SelectionKey) ite.next();
            // 移除，防止重复处理
            ite.remove();

            SocketChannel channel = (SocketChannel) key.channel();

            int ret = 0;
            boolean failure = true;
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            try {
                ret = channel.read(buffer);
                failure = false;
            } catch (Exception e) {
                // ignore
            }
            //判断是否连接已断开
            if (ret <= 0 || failure) {
                key.cancel();
                System.out.println("客户端断开连接");
            } else {
                System.out.println("收到数据:" + new String(buffer.array()));

                //回写数据
                ByteBuffer outBuffer = ByteBuffer.wrap("收到\n".getBytes());
                channel.write(outBuffer);
            }
        }
    }


    @Override
    public void registerNewChannelTask(final SocketChannel channel) {
        final Selector selector = this.selector;
        registerTask(() -> {
            try {
                channel.register(selector, SelectionKey.OP_READ);
            } catch (ClosedChannelException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected int select(Selector selector) throws IOException {
        return selector.select(500);
    }

}

