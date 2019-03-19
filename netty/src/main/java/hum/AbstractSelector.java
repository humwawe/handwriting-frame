package hum;

import hum.pool.SelectorRunnablePool;

import java.io.IOException;
import java.nio.channels.Selector;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author hum
 */
public abstract class AbstractSelector implements Runnable {

    private final Executor executor;
    protected Selector selector;
    protected final AtomicBoolean wakenUp = new AtomicBoolean();

    private final Queue<Runnable> taskQueue = new ConcurrentLinkedQueue<>();
    private String threadName;
    protected SelectorRunnablePool selectorRunnablePool;

    AbstractSelector(Executor executor, String threadName, SelectorRunnablePool selectorRunnablePool) {
        this.executor = executor;
        this.threadName = threadName;
        this.selectorRunnablePool = selectorRunnablePool;
        openSelector();
    }

    private void openSelector() {
        try {
            this.selector = Selector.open();
        } catch (IOException e) {
            throw new RuntimeException("Failed to create a selector.");
        }
        executor.execute(this);
    }

    @Override
    public void run() {
        Thread.currentThread().setName(this.threadName);
        while (true) {
            try {
                wakenUp.set(false);
                select(selector);
                processTaskQueue();
                process(selector);
            } catch (Exception e) {
                // ignore
            }
        }

    }

    protected final void registerTask(Runnable task) {
        taskQueue.add(task);
        Selector selector = this.selector;
        if (selector != null) {
            if (wakenUp.compareAndSet(false, true)) {
                selector.wakeup();
            }
        } else {
            taskQueue.remove(task);
        }
    }

    private void processTaskQueue() {
        for (; ; ) {
            final Runnable task = taskQueue.poll();
            if (task == null) {
                break;
            }
            task.run();
        }
    }

    public SelectorRunnablePool getSelectorRunnablePool() {
        return selectorRunnablePool;
    }


    protected abstract int select(Selector selector) throws IOException;

    protected abstract void process(Selector selector) throws IOException;

}
