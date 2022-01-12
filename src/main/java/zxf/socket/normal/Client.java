package zxf.socket.normal;

import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 8888)) {

        } catch (Exception e) {
            System.out.println("Exception when connect to a server");
        }
    }
}
