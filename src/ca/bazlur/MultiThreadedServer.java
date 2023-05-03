package ca.bazlur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadedServer {
    public static void main(String[] args) throws IOException {
        var server = new ServerSocket(8080);

        while (true) {
            var socket = server.accept();
            var thread = new Thread(() -> handle(socket));
            thread.start();
        }
    }

    private static void handle(Socket socket) {
        System.out.println("Handling request from " + socket);

        try (var out = new PrintStream(socket.getOutputStream());
             var in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            var request = "";
            while ((request = in.readLine()) != null) {
                System.out.println(request);
                if (request.contains("exit")) {
                    break;
                }
                var upperCase = request.toUpperCase();
                out.println(upperCase);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
