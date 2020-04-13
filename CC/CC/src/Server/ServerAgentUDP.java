/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Common.PDU;
import Common.Resources;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author joanacruz
 */
public class ServerAgentUDP extends Thread{
    private Resources connResources;
    private int clientPort = 7778;
    private PDU packet;
    private static Scanner scanner = new Scanner(System.in);
    
    public ServerAgentUDP() throws SocketException, UnknownHostException {
        connResources = new Resources(7777, null);
        packet = new PDU();
    }
    
    public void run() {
        boolean running = true;
        String input = null;
        try {
            while(running) {
                connResources.receive();                
                packet = connResources.getPacketReceive();
                        
                /* Método que declara o início de uma conexão
                 * Recebe um segmento do tipo SYN de um cliente
                 * Envia ao cliente um segmento do tipo SYN + ACK
                 */
                if(packet.getFlagType() == 0){
                    System.out.println("Want to accept connection from client in address " +  connResources.getAddressSend().getHostAddress() +
                            " and port " + connResources.getPortSend());
                    input = scanner.nextLine();
                    if(input.matches("(yes|y|sim)")){
                        System.out.println("Accepted conection from client in address " +  connResources.getAddressSend() + " and port " + connResources.getPortSend() + "!");
                        packet.synPacket(clientPort);
                        connResources.sendAndExpect(packet, 3000, 5);
                        System.out.println("The server now will answer to client in port " + clientPort + "!");
                        ClientHandler handler = new ClientHandler(connResources.getAddressSend(), clientPort++);
                        handler.start();
                    }
                    else{
                        System.out.println("Refused connection");
                    }
                }                                                
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
