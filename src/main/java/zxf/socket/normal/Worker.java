package zxf.socket.normal;

import java.io.IOException;
import java.net.Socket;

public class Worker implements Runnable {
    private Socket socket;

    public Worker(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Exception when process a socket");
        }
    }
}
