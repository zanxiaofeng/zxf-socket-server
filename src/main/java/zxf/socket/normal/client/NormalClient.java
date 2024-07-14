package zxf.socket.normal.client;

import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class NormalClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 8888);
             Scanner in = new Scanner(socket.getInputStream(), StandardCharsets.UTF_8)) {
            String line = in.nextLine();
            System.out.println(line);

        } catch (Exception e) {
            System.out.println("Exception when connect to a server");
        }
    }
}
