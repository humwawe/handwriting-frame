package hum;

import hum.pool.SelectorRunnablePool;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;


/**
 * @author hum
 */
public class Start {

    public static void main(String[] args) {

        SelectorRunnablePool selectorRunnablePool = new SelectorRunnablePool(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
        ServerBootstrap bootstrap = new ServerBootstrap(selectorRunnablePool);

        bootstrap.bind(new InetSocketAddress(10101));

        System.out.println("start");
    }

}

