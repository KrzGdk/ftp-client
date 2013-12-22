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
        try (Socket server = new Socket("localhost", 7777)) {
            PrintWriter to_server = new PrintWriter(server.getOutputStream(), true);
            BufferedReader from_server = new BufferedReader(new InputStreamReader(server.getInputStream()));
            to_server.println("aaa");
            System.out.println(from_server.readLine());
            to_server.println("bbb");
            System.out.println(from_server.readLine());
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
