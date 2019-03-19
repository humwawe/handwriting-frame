package hum.pool;

import hum.ServerBoss;
import hum.ServerWorker;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hum
 */
public class SelectorRunnablePool {

    private final AtomicInteger bossIndex = new AtomicInteger();
    private Boss[] bosses;

    private final AtomicInteger workerIndex = new AtomicInteger();
    private Worker[] workeres;


    public SelectorRunnablePool(Executor boss, Executor worker) {
        initBoss(boss, 1);
        initWorker(worker, Runtime.getRuntime().availableProcessors() * 2);
    }

    private void initBoss(Executor boss, int count) {
        this.bosses = new ServerBoss[count];
        for (int i = 0; i < bosses.length; i++) {
            bosses[i] = new ServerBoss(boss, "boss thread " + (i + 1), this);
        }

    }

    private void initWorker(Executor worker, int count) {
        this.workeres = new ServerWorker[count];
        for (int i = 0; i < workeres.length; i++) {
            workeres[i] = new ServerWorker(worker, "worker thread " + (i + 1), this);
        }
    }

    public Worker nextWorker() {
        return workeres[Math.abs(workerIndex.getAndIncrement() % workeres.length)];

    }

    public Boss nextBoss() {
        return bosses[Math.abs(bossIndex.getAndIncrement() % bosses.length)];
    }

}

