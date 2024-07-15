package zxf.socket.normal.client;

import jdk.net.ExtendedSocketOptions;

import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class NormalClient {
    public static void main(String[] args) {
        //There is no connection timeout when using ctor
        try (Socket socket = new Socket("127.0.0.1", 8888);
             Scanner in = new Scanner(socket.getInputStream(), StandardCharsets.UTF_8)) {
            System.out.println("Connected.");
            //Disable client side keep alive
            socket.setKeepAlive(false);
            while (true) {
                System.out.println("Start reading.");
                String line = in.nextLine();
                System.out.println(line);
                Thread.sleep(60000);
            }
        } catch (Exception e) {
            System.out.println("Exception when connect to a server");
        }
    }
}
