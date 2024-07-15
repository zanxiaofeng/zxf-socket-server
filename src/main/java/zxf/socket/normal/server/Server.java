package zxf.socket.normal.server;

import jdk.net.ExtendedSocketOptions;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    private Boolean isRunning = true;

    public void launch(int port) throws IOException {
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started at " + port);
            while (isRunning) {
                Socket socket = serverSocket.accept();
                //Enable&setup server side keep alive
                socket.setKeepAlive(true);
                socket.setOption(ExtendedSocketOptions.TCP_KEEPIDLE, 60);
                System.out.println("remote::" + socket.getRemoteSocketAddress());
                executorService.execute(new Worker(socket));
            }
        }
    }
}
