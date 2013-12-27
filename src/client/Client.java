/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Krzysiek
 */
public class Client{
    public static void main(String[] args){
        try (Socket server = new Socket("localhost", 21)) {
            PrintWriter toServer = new PrintWriter(server.getOutputStream(), true);
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));
            String command, response;
            while(true){
                command = stdin.readLine();
                toServer.println(command);
                response = fromServer.readLine();
                System.out.println(response);
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
