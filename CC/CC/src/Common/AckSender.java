/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author joanacruz
 */
public class AckSender extends Thread{
    private Resources connResources;
    private PDU packet;
    private Map<Integer, PDU> packetsList;
    private AtomicBoolean transferComplete;
    
    public AckSender(Resources connection, Map<Integer, PDU> packets, AtomicBoolean transfer) throws UnknownHostException, SocketException{
        connResources = connection;
        packet = new PDU();
        packetsList = packets;
        transferComplete = transfer;
    }
      
    @Override
    public void run(){
        try {
            while(transferComplete.get()){
                Collection<PDU> receivedPDUs = packetsList.values();
                for(PDU pdu : receivedPDUs){
                    if(pdu.getFlagType() == 2){    
                        packet = pdu;
                        packet.ackPacket();
                        connResources.send(packet);
                        packetsList.remove(packet.getSeqNumber());
                    }
                }
            }
            packet = new PDU();
            packet.setFlagType(9);
            connResources.sendAttemps(packet, 3);
        } catch (Exception ex){
            ex.printStackTrace();
        } 
    }
}
