/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Common.AckReceiver;
import Common.PDU;
import Common.FileReceiver;
import Common.Resources;
import Common.AckSender;
import Common.FileSender;
import static Common.Resources.FILES_FOLDER_CLIENT;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author joanacruz
 */
public class ClientAgentUDP {

    private final int serverPort = 7777;
    private PDU packet;
    private Resources connResources;
    private Map<Integer, PDU> packetsList;

    public ClientAgentUDP(String serverHost, int port) throws UnknownHostException, SocketException {
        packet = new PDU();
        connResources = new Resources(port, InetAddress.getByName(serverHost));
        connResources.setPortSend(serverPort);
    }

    public void setPacket(PDU pdu) {
        packet = pdu;
    }

    public PDU getPacket() {
        return packet;
    }

    public void send() throws IOException {
        connResources.send(packet);
    }
    
    public void sendAndExpect() throws IOException {
        connResources.sendAndExpect(packet, 3000, 5);
    }
    
    public void receive() throws IOException {
        connResources.receive();
        packet = connResources.getPacketReceive();
    }

    public void closeConnection() {
        connResources.close();
    }

    public void downloadFile(String fileName) throws UnknownHostException, SocketException, InterruptedException {
        packetsList = new ConcurrentHashMap<>();
        AtomicBoolean transfer = new AtomicBoolean(true);
        FileReceiver fileReceiver = new FileReceiver(connResources, packetsList, transfer, FILES_FOLDER_CLIENT + fileName);
        AckSender ackSender = new AckSender(connResources, packetsList, transfer);
        fileReceiver.start();
        ackSender.start();
        fileReceiver.join();
        ackSender.join();
    }

    public void uploadFile(String fileName) throws UnknownHostException, InterruptedException, SocketException{
        packetsList = new ConcurrentHashMap<>();
        AtomicBoolean endOfTransfer = new AtomicBoolean(false);
        FileSender fileSender = new FileSender(connResources, packetsList, endOfTransfer, FILES_FOLDER_CLIENT + fileName);
        AckReceiver ackReceiver = new AckReceiver(connResources, packetsList, endOfTransfer);
        fileSender.start();
        ackReceiver.start();
        fileSender.join();
        ackReceiver.join();      
    }
    
    /*Método para início de uma conexão
      Cliente manda um segmento do tipo SYN para se tentar conectar com o servidor
      Timeout = 5 segundos (tempo de enviar o segmento de início de conexão e receber um SYN + ACK do servidor)
      Número de tentativas = 5
    */
    public boolean connect() {
        try {
            connResources.sendAndExpect(packet, 5000, 5);
            packet.ackPacket();
            connResources.sendAttemps(packet, 3);
            packet = connResources.getPacketReceive();
            int newPort = packet.getPort();
            connResources.setPortSend(newPort);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean disconnect() {
        try {
            packet.setFlagType(7);
            connResources.sendAndExpect(packet, 5000, 5);
            packet.ackPacket();
            connResources.sendAttemps(packet, 3);
            packet.setFlagType(3);
            connResources.sendAndExpect(packet, 5000, 5);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
