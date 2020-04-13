
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author joanacruz
 */
public class AckReceiver extends Thread{
    private Resources connResources;
    private PDU packet;
    private Map<Integer, PDU> packetsList;
    private AtomicBoolean endOfTransfer;
    
    public AckReceiver(Resources connection, Map<Integer, PDU> packets, AtomicBoolean end) throws UnknownHostException, SocketException{
        connResources = connection;
        packet = new PDU();
        packetsList = packets;
        endOfTransfer = end;
    }
    
    @Override
    public void run(){
        boolean running = true;
        try {
            while(running){
                if(!connResources.receive(1000)) System.out.println("TimeOut !");;
                packet = connResources.getPacketReceive();
                int ackNumber = packet.getAckNumber();
                packetsList.remove(ackNumber);
                if(packetsList.isEmpty()) running = false;
            }
            packet = new PDU();
            packet.setFlagType(9);
            if(connResources.sendAndExpect(packet, 1000, 5) && connResources.getPacketReceive().getFlagType() == 9) endOfTransfer.set(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
