package zxf.socket.normal.client;

import jdk.net.ExtendedSocketOptions;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClientWithTimeout {
    public static void main(String[] args) {
        try (Socket socket = new Socket()) {
            //Enable&setup client side keep alive
            socket.setKeepAlive(true);
            socket.setOption(ExtendedSocketOptions.TCP_KEEPIDLE, 60);
            //For connect timeout
            socket.connect(new InetSocketAddress("127.0.0.1", 8888), 10000);
            //For read timeout
            socket.setSoTimeout(5000);

            System.out.println("Connected.");

            try (Scanner in = new Scanner(socket.getInputStream(), StandardCharsets.UTF_8)) {
                while (true) {
                    System.out.println("Start reading.");
                    String line = in.nextLine();
                    System.out.println(line);
                    Thread.sleep(60000);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception when connect to a server");
        }
    }
}
