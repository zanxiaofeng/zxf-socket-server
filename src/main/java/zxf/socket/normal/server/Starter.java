package zxf.socket.normal.server;

import java.io.IOException;

public class Starter {
    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.launch(8888);
        } catch (IOException e) {
            System.out.println("Exception when launch a socket server");
        }
    }
}
