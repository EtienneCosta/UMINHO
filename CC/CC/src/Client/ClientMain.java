/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Common.PDU;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author joanacruz
 */
public class ClientMain {

    private static Scanner scanner = new Scanner(System.in);
    private static String input;

    public static void main(String[] args) throws IOException {
        String serverHost = "localhost";
        int clientPort = 5000;
        if (args.length > 0) {
            serverHost = args[0];
        }
        if (args.length > 1) {
            clientPort = Integer.parseInt(args[1]);
        }
        ClientAgentUDP client = new ClientAgentUDP(serverHost, clientPort);

        if (!client.connect()) {
            System.out.println("Could not connect to server");
            return;
        }
        try {
            PDU pdu = new PDU();
            do {
                System.out.print("JOE > ");
                input = scanner.nextLine();
                if (input.equals("help")) {
                    System.out.println("|---------------- HELP MENU ----------------|\n"
                            + "| Comands                                   |\n"
                            + "| --> download <fileName>                   |\n"
                            + "| --> download <fileName> <remoteName>      |\n"
                            + "| --> upload <fileName>                     |\n"
                            + "| --> list                                  |\n"
                            + "| --> exit                                  |\n"
                            + "|-------------------------------------------|");
                } else {
                    pdu.protocolarPacket(input);
                    client.setPacket(pdu);
                    client.sendAndExpect();
                    if (input.matches("download [A-Za-z0-9._]+( [A-Za-z0-9._]+)?")) {
                        String[] files = input.split(" ");                        
                        if (files.length > 2) {
                            client.downloadFile(files[2]);
                        } else {
                            client.downloadFile(files[1]);
                        }
                    }
                    if (input.equals("list")) {
                        client.receive();
                        pdu = client.getPacket();
                        String[] filesList = new String(pdu.getFileData()).split(";");
                        System.out.println("Files List");
                        for (String file : filesList) {
                            System.out.println(file);
                        }
                    }
                    if (input.matches("upload [A-Za-z0-9.]+")) {
                        client.uploadFile(input.split(" ")[1]);
                    }

                }
            } while (!input.equals("exit"));
            if (!client.disconnect()) {
                System.out.println("Server is offline but the connection ended");
            } else {
                System.out.println("Connection successfully closed");
            }
            client.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
