/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 *
 * @author joanacruz
 */
public class PDU {

    private int seqNumber;
    private int ackNumber;
    private int flagType;
    private int port;
    private int windowSize;
    private int lengthData;
    private long checksum;
    private byte[] fileData;

    public PDU() {
        this.seqNumber = 0;
        this.ackNumber = 0;
        this.flagType = 0;
        this.port = 0;
        this.windowSize = 0;
        this.lengthData = 0;
        this.checksum = 0;
        this.fileData = new byte[1024];
    }

    public PDU(PDU packet) {
        this.seqNumber = packet.getSeqNumber();
        this.ackNumber = packet.getAckNumber();
        this.flagType = packet.getFlagType();
        this.port = packet.getPort();
        this.windowSize = packet.getWindowSize();
        this.lengthData = packet.getLengthData();
        this.checksum = packet.getChecksum();
        this.fileData = packet.getFileData();
    }

    public int getSeqNumber() {
        return seqNumber;
    }

    public void setSeqNumber(int seqNumber) {
        this.seqNumber = seqNumber;
    }

    public int getAckNumber() {
        return ackNumber;
    }

    public void setAckNumber(int ackNumber) {
        this.ackNumber = ackNumber;
    }

    public int getFlagType() {
        return flagType;
    }

    public void setFlagType(int flagType) {
        this.flagType = flagType;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getWindowSize() {
        return windowSize;
    }

    public void setWindowSize(int windowSize) {
        this.windowSize = windowSize;
    }

    public int getLengthData() {
        return lengthData;
    }

    public void setLengthData(int lengthData) {
        this.lengthData = lengthData;
    }
    
    public long getChecksum() {
        return checksum;
    }

    public void setChecksum(long checksum) {
        this.checksum = checksum;
    }

    public byte[] getFileData() {
        byte[] res = Arrays.copyOf(this.fileData, this.lengthData);
        return res;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = Arrays.copyOf(fileData, this.lengthData);
    }

    public String getTypeOfPDU() {
        String result;
        switch (this.flagType) {
            case 0:
                result = "SYN";
                break;
            case 1:
                result = "ACK";
                break;
            case 2:
                result = "PSH";
                break;
            case 3:
                result = "FIN";
                break;
            case 4:
                result = "SYN + ACK";
                break;
            case 5:
                result = "JOE DOWN";
                break;
            case 6:
                result = "JOE UP";
                break;
            case 7:
                result = "EXIT";
                break;
            case 8:
                result = "LIST";
                break;
            case 9:
                result = "END TRANSF";
                break;
            default:
                result = "NOTHING";
        }
        return result;
    }

    public void protocolarPacket(String message) {
        String m = message.split(" ")[0];

        if (m.equals("download")) {
            this.setFlagType(5);
        } else if (m.equals("upload")) {
            this.setFlagType(6);
        } else if (m.equals("list")) {
            this.setFlagType(8);
        } else {
            this.setFlagType(7);
        }

        this.setAckNumber(1);
        this.setSeqNumber(1);
        this.setLengthData(message.getBytes().length);       
        this.setFileData(message.getBytes());
    }

    public void ackPacket() {
        this.setAckNumber(this.seqNumber);
        this.setFlagType(1);
    }

    public void synPacket(int clientPort) {
        this.setSeqNumber(0);
        this.setAckNumber(1);
        this.setFlagType(4);
        this.setPort(clientPort);
    }

    public byte[] PDUToByte() {
        ByteBuffer buffer = ByteBuffer.allocate(1056);
        buffer.put(ByteBuffer.allocate(4).putInt(this.getSeqNumber()).array());
        buffer.put(ByteBuffer.allocate(4).putInt(this.getAckNumber()).array());
        buffer.put(ByteBuffer.allocate(4).putInt(this.getFlagType()).array());
        buffer.put(ByteBuffer.allocate(4).putInt(this.getPort()).array());
        buffer.put(ByteBuffer.allocate(4).putInt(this.getWindowSize()).array());
        buffer.put(ByteBuffer.allocate(4).putInt(this.getLengthData()).array());
        buffer.put(ByteBuffer.allocate(8).putLong(this.getChecksum()).array());
        buffer.put(ByteBuffer.allocate(1024).put(this.getFileData()).array());

        return buffer.array();
    }

    public void ByteToPDU(byte[] dataPacket) {
        this.setSeqNumber(ByteBuffer.wrap(Arrays.copyOfRange(dataPacket, 0, 4)).getInt());
        this.setAckNumber(ByteBuffer.wrap(Arrays.copyOfRange(dataPacket, 4, 8)).getInt());
        this.setFlagType(ByteBuffer.wrap(Arrays.copyOfRange(dataPacket, 8, 12)).getInt());
        this.setPort(ByteBuffer.wrap(Arrays.copyOfRange(dataPacket, 12, 16)).getInt());
        this.setWindowSize(ByteBuffer.wrap(Arrays.copyOfRange(dataPacket, 16, 20)).getInt());
        this.setLengthData(ByteBuffer.wrap(Arrays.copyOfRange(dataPacket, 20, 24)).getInt());
        this.setChecksum(ByteBuffer.wrap(Arrays.copyOfRange(dataPacket, 24, 32)).getLong());
        this.setFileData(ByteBuffer.wrap(Arrays.copyOfRange(dataPacket, 32, dataPacket.length)).array());
    }

    public PDU clone() {
        return new PDU(this);
    }
}
