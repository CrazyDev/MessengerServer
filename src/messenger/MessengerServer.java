/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hugo
 */
public class MessengerServer {

    public static boolean closeServer;

    public static Map<String, Thread> threads;
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        threads = new HashMap<>();
        Server server = new Server("AVLIS", "");
        System.out.println(server.getName() + ": Created  server");
        try {
            server.openServer();
            System.out.println(server.getName() + ": Opened  server");
        } catch (IOException ex) {
            Logger.getLogger(MessengerServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            while (!closeServer) {
                Socket client = server.getServerSocket().accept();
                if (client != null) {
                    System.out.println(server.getName() + ": New connection by " + client.getInetAddress().getHostAddress());

                    Thread thread = new Thread(new ClientHandler(client.getInputStream(), server));
                    threads.put(client.getLocalAddress().getHostName(), thread);
                    thread.start();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    String input = reader.readLine();
                    if (input != null) {
                        String message = "[" + server.getName() + "] [" + getData(System.currentTimeMillis()) + "] " + input;
                        System.out.println(message);
                    }
                    server.clients.add(client);
                }
            }
            try {
                server.getServerSocket().close();
            } catch (IOException ex) {
                Logger.getLogger(MessengerServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(MessengerServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getData(long l) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(l);
        return simpleDateFormat.format(calendar.getTime());
    }

}
