package zxf.socket.normal.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Worker implements Runnable {
    private Socket socket;

    public Worker(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            try (OutputStream outputStream = socket.getOutputStream()) {
                while (true) {
                    outputStream.write("Hello Davis".getBytes(StandardCharsets.UTF_8));
                    outputStream.flush();
                    System.out.println("Write hello!");
                    Thread.sleep(60000);
                }
            }
            //socket.close();
        } catch (IOException e) {
            System.out.println("Exception when process a socket");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
