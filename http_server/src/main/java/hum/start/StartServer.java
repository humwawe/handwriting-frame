package hum.start;

import hum.core.Server;

/**
 * @author hum
 */
public class StartServer {
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
