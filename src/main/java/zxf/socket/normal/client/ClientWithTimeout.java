package zxf.socket.normal.client;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClientWithTimeout {
    public static void main(String[] args) {
        try (Socket socket = new Socket()) {
            //for connect timeout
            socket.connect(new InetSocketAddress("127.0.0.1", 8888), 10000);
            //for read timeout
            socket.setSoTimeout(5000);

            try (Scanner in = new Scanner(socket.getInputStream(), StandardCharsets.UTF_8)) {
                String line = in.nextLine();
                System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println("Exception when connect to a server");
        }
    }
}
