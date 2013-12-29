/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Krzysiek
 */
public class Client{
    private static ServerSocket dataSocket = null;
    private static int clientPort;
    
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
                if(command.startsWith("PORT ")){
                    String address = command.substring(5);
                    String[] splitAddress = address.split(",");
                    int p1 = Integer.parseInt(splitAddress[4]);
                    int p2 = Integer.parseInt(splitAddress[5]);
                    clientPort = (p1 << 8) + p2; 
                    if(dataSocket == null){
                        dataSocket = new ServerSocket(clientPort);
                    }
                }
                else if(command.startsWith("RETR ")){
                    String filename = command.substring(5);
                    
                    RandomAccessFile file = new RandomAccessFile((filename),"rw");
                    
                    Socket dataChannel = dataSocket.accept();
                    DataInputStream dataIn = new DataInputStream(dataChannel.getInputStream());

                    int offset;
                    byte[] data = new byte[1024];
                    while( (offset = dataIn.read(data)) != -1){
                        file.write(data, 0, offset);
                    }
                    file.close();
                }
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
