package ca.bazlur;

import java.io.IOException;
import java.net.Socket;

public class DDosAttackSimulator {
    public static void main(String[] args) throws IOException {

        Socket sockets[] = new Socket[20_000];
        for (int i = 0; i < sockets.length; i++) {
            sockets[i] = new Socket("localhost", 8888);
        }
    }
}
