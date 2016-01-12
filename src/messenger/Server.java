/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messenger;

import static messenger.MessengerServer.getData;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Hugo
 */
public class Server {

    private final String name;
    private final String host;

    private ServerSocket server;

    public Set<Socket> clients;

    public Server(String name, String host) {
        this.name = name;
        this.host = host;
        this.clients = new HashSet<>();
    }

    public String getName() {
        return this.name;
    }

    public ServerSocket getServerSocket() {
        return this.server;
    }

    public void openServer() throws IOException {
        server = new ServerSocket(1055);
    }

    public void sendMessage(String message) throws IOException {
        for (Socket socket : clients) {
            PrintStream stream = new PrintStream(socket.getOutputStream());
            message = "[" + getName() + "] [" + MessengerServer.getData(System.currentTimeMillis()) + "] " + message;
            stream.println(message);
            System.out.println(message);
        }
    }

}
