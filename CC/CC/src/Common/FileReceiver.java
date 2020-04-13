/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.io.File;
import java.io.FileOutputStream;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.CRC32;

/**
 *
 * @author joanacruz
 */
public class FileReceiver extends Thread {

    private Resources connResources;
    private PDU packet;
    private Map<Integer, PDU> packetsList;
    private Map<Integer, PDU> packetsFinal;
    private byte[] buffer;
    private AtomicBoolean transferComplete;
    private String path;

    public FileReceiver(Resources connection, Map<Integer, PDU> packets, AtomicBoolean transfer, String FilePath) throws UnknownHostException, SocketException {
        connResources = connection;
        packet = new PDU();
        packetsList = packets;
        packetsFinal = new ConcurrentHashMap<>();
        transferComplete = transfer;
        buffer = new byte[1024];
        path = FilePath;
    }

    @Override
    public void run() {
        try {
            FileOutputStream fos = null;
            File file = new File(path);
            file.createNewFile();
            fos = new FileOutputStream(file);
            while (transferComplete.get()) {
                connResources.receive();
                packet = connResources.getPacketReceive();
                if (packet.getFlagType() == 2) {
                    long checksumReceived = packet.getChecksum();
                    CRC32 checksum = new CRC32();
                    checksum.update(packet.getSeqNumber());
                    checksum.update(packet.getFileData());
                    long calculatedChecksum = checksum.getValue();
                    if (checksumReceived == calculatedChecksum) {
                        packetsList.put(packet.getSeqNumber(), packet.clone());
                        packetsFinal.put(packet.getSeqNumber(), packet.clone());
                    } else {
                        System.out.println("Corrupted packet --> Checksum Failed");
                    }
                }
                if (packet.getFlagType() == 9) {
                    transferComplete.set(false);
                }

            }
            for (int i = 1; i <= packetsFinal.size(); i++) {
                PDU pdu = packetsFinal.get(i);
                buffer = pdu.getFileData().clone();
                fos.write(buffer, 0, pdu.getLengthData());
            }
            fos.flush();
            fos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
