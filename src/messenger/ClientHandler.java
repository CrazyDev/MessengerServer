/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messenger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hugo
 */
public class ClientHandler implements Runnable {

    private final Server server;
    private final InputStream stream;

    public ClientHandler(InputStream stream, Server server) {
        this.server = server;
        this.stream = stream;
    }

    @Override
    public void run() {
        Scanner s = new Scanner(stream);
        while(s.hasNextLine()){
            try {
                server.sendMessage(s.nextLine());
            } catch (IOException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        s.close();
    }

}
