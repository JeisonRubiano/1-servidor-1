package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {

    private ServerSocket serverSocket;
    public static final int PORT = 4200;
    private ArrayList<ClientWorker> connections;

    public Server() throws IOException {
        serverSocket = new ServerSocket(PORT);
        connections = new ArrayList<>();
        acceptConnections();
    }

    private void acceptConnections() {
        new Thread(() -> {
            while (!serverSocket.isClosed()) {
                try {
                    ClientWorker client = new ClientWorker(serverSocket.accept());
                    new Thread(client).start();
                    connections.add(client);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        System.out.println("inicio server...");
        try {
            new Server();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
