/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Common.AckReceiver;
import Common.AckSender;
import Common.FileReceiver;
import Common.FileSender;
import Common.PDU;
import Common.Resources;
import static Common.Resources.FILES_FOLDER_SERVER;
import java.io.File;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;


/*
 *
 * @author joanacruz
 */
public class ClientHandler extends Thread {

    private Resources connResources;
    private PDU packet;

    ClientHandler(InetAddress addressSend, int port) throws UnknownHostException, SocketException {
        connResources = new Resources(port, addressSend);
    }

    @Override
    public void run() {
        try {
            boolean running = true;
            try {
                while (running) {
                    connResources.receive();
                    packet = connResources.getPacketReceive();
                    Map<Integer, PDU> packetsList = new ConcurrentHashMap<>();
                    AtomicBoolean endOfTransfer = new AtomicBoolean(false);
                    PDU ack = packet.clone();
                    ack.ackPacket();
                    switch (packet.getFlagType()){
                        /* Termina conexão */
                        case 3:
                            packet.ackPacket();
                            connResources.sendAttemps(packet,3);
                            System.out.println("Socket closed with ports " + packet.getPort() + " " + connResources.getPortSend());
                            connResources.close();
                            running = false;
                            break;
                        /* Download de um ficheiro */
                        case 5:
                            connResources.send(ack);
                            String fileName = new String(packet.getFileData()).split(" ")[1];
                            AckReceiver in = new AckReceiver(connResources, packetsList, endOfTransfer);
                            FileSender out = new FileSender(connResources, packetsList, endOfTransfer, FILES_FOLDER_SERVER + fileName);
                            in.start();
                            out.start();
                            in.join();
                            out.join();
                            break;
                        /* Upload de um ficheiro */
                        case 6:
                            connResources.send(ack);
                            String file = new String(packet.getFileData()).split(" ")[1];
                            AtomicBoolean transfer = new AtomicBoolean(true);
                            FileReceiver fileReceiver = new FileReceiver(connResources, packetsList, transfer, FILES_FOLDER_SERVER + file);
                            AckSender ackSender = new AckSender(connResources, packetsList, transfer);
                            fileReceiver.start();
                            ackSender.start();
                            fileReceiver.join();
                            ackSender.join();
                            break;
                        /* Lista de ficheiros */    
                        case 8:
                            connResources.send(ack);
                            File folder = new File(FILES_FOLDER_SERVER);
                            File[] listOfFiles = folder.listFiles();
                            String filesList = "";
                            for (int i = 0; i < listOfFiles.length; i++) {
                                if (listOfFiles[i].isFile()) {
                                    filesList += listOfFiles[i].getName() + ";";
                                }
                            }
                            packet.setLengthData(filesList.getBytes().length);
                            packet.setFileData(filesList.getBytes());
                            connResources.send(packet);
                            break;
                        /* Recebe PDU do tipo EXIT */
                        case 7:
                            connResources.send(ack);
                            packet.setFlagType(3);
                            connResources.send(packet);
                            break;
                        default:
                            break;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
