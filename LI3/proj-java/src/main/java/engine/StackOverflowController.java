package engine;

import common.Pair;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import li3.TADCommunity;

public class StackOverflowController {

    private Scanner input = new Scanner(System.in);
    private TADCommunity model;
    private StackOverflowView viewTxt;

    public void setModel(TADCommunity alunos) {
        model = alunos;
    }

    public void setView(StackOverflowView txtMenus) {
        viewTxt = txtMenus;
    }

    // Método invocado pelo programa principal para execução
    public void startFlow() {
        // Início do fluxo de execução
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        Menu menu = viewTxt.getStackOverflowView();
        String opcao;
        do {
            menu.show();
            opcao = input.nextLine();
            opcao = opcao.toUpperCase();
            switch (opcao) {
                case "1":
                    System.out.println("Insira um ID de um Post: ");
                    Long id = input.nextLong();
                    input.nextLine();
                    try {
                        Pair<String, String> res = model.infoFromPost(id);
                        System.out.println("O título do Post é ->" + res.getFst()
                                + " e o utilizador correspondente é -> " + res.getSnd());
                    } catch (PostInexistenteException e) {
                        System.out.println("O ID de Post fornecido " + e.getMessage() + " não existe!!!");
                    }
                    break;
                case "2":
                    System.out.println("Insira o número de utilizadores:");
                    int n = input.nextInt();
                    input.nextLine();
                    System.out.println("Os top N utilizadores com maior número de posts de sempre são: ");
                    List<Long> res1 = model.topMostActive(n);
                    for (Long l : res1) {
                        System.out.println("Utilizador: " + l);
                    }
                    break;
                case "3":
                    System.out.println("Insira a data inicial(dd-mm-aaaa): ");
                    String data1 = input.nextLine();
                    LocalDate date1 = LocalDate.parse(data1, formatter);
                    System.out.println("Insira a data final(dd-mm-aaaa): ");
                    String data2 = input.nextLine();
                    LocalDate date2 = LocalDate.parse(data2, formatter);
                    Pair<Long, Long> res2 = model.totalPosts(date1, date2);
                    System.out.println("Questions: " + res2.getFst() + " Answers: " + res2.getSnd());
                    break;

                case "4":

                    System.out.println("Insira a data inicial(dd-mm-aaaa): ");
                    String data3 = input.nextLine();
                    LocalDate date3 = LocalDate.parse(data3, formatter);
                    System.out.println("Insira a data final(dd-mm-aaaa): ");
                    String data4 = input.nextLine();
                    LocalDate date4 = LocalDate.parse(data4, formatter);
                    String tag4;
                    System.out.println("Indique o nome da tag: ");
                    tag4 = input.nextLine();
                    List<Long> res4 = model.questionsWithTag(tag4, date3, date4);

                    for (Long r : res4) {
                        System.out.println("Id: " + r);
                    }

                    break;
                case "5":
                    System.out.println("Insira um ID de um User: ");
                    Long id5 = input.nextLong();
                    input.nextLine();
                    try {
                        Pair<String, List<Long>> res5 = model.getUserInfo(id5);

                        System.out.println("Bio: " + res5.getFst());
                        for (Long r : res5.getSnd()) {
                            System.out.println("Id:" + r);
                        }
                    } catch (UserInexistenteException e) {
                        System.out.println("O ID de utilizador fornecido " + e.getMessage() + " não existe!!!");
                    }
                    break;

                case "6":
                    System.out.println("Insira a data inicial(dd-mm-aaaa): ");
                    String data6 = input.nextLine();
                    LocalDate date6 = LocalDate.parse(data6, formatter);
                    System.out.println("Insira a data final(dd-mm-aaaa): ");
                    String data66 = input.nextLine();
                    LocalDate date66 = LocalDate.parse(data66, formatter);
                    System.out.println("Indique o valor de N:");
                    int n6 = input.nextInt();
                    input.nextLine();
                    List<Long> res6 = model.mostVotedAnswers(n6, date6, date66);
                    for (Long r : res6) {
                        System.out.println("Id:" + r);
                    }
                    break;

                case "7":
                    System.out.println("Insira a data inicial(dd-mm-aaaa): ");
                    String data7 = input.nextLine();
                    LocalDate date7 = LocalDate.parse(data7, formatter);
                    System.out.println("Insira a data final(dd-mm-aaaa): ");
                    String data77 = input.nextLine();
                    System.out.println("Indique o valor de N:");
                    int n7 = input.nextInt();
                    input.nextLine();
                    LocalDate date77 = LocalDate.parse(data77, formatter);
                    List<Long> res7 = model.mostAnsweredQuestions(n7, date7, date77);
                    for (Long r : res7) {
                        System.out.println("Id:" + r);
                    }
                    break;

                case "8":
                    System.out.println("Indique uma palavra: ");
                    String word = input.nextLine();

                    System.out.println("Indique o valor de N: ");
                    int n8 = input.nextInt();
                    input.nextLine();
                    List<Long> res8 = model.containsWord(n8, word);
                    for (Long r : res8) {
                        System.out.println("Id: " + r);
                    }
                    break;
                case "9":
                    System.out.println("Indique o valor de N: ");
                    int n9 = input.nextInt();
                    System.out.println("Indique o primeiro id: ");
                    long id9 = input.nextLong();
                    System.out.println("Indique o segundo id: ");
                    long id99 = input.nextLong();
                    input.nextLine();
                    try {
                        List<Long> res9 = model.bothParticipated(n9, id9, id99);
                        for (Long r : res9) {
                            System.out.println("Id: " + r);
                        }
                    } catch (UserInexistenteException e) {
                        System.out.println("O ID de utilizador fornecido " + e.getMessage() + " não existe!!!");
                    }
                    break;

                case "10":
                    System.out.println("Indique o valor do id: ");
                    long id10 = input.nextLong();
                    input.nextLine();
                    try {
                        long result = model.betterAnswer(id10);
                        System.out.print("Melhor Resposta: " + result);
                    } catch (PostInexistenteException e) {
                        System.out.println("O ID de Post fornecido " + e.getMessage() + " não existe!!!");
                    }
                    break;
                case "11":
                    System.out.println("Insira a data inicial(dd-mm-aaaa): ");
                    String data11 = input.nextLine();
                    LocalDate date11 = LocalDate.parse(data11, formatter);
                    System.out.println("Insira a data final(dd-mm-aaaa): ");
                    String data111 = input.nextLine();
                    LocalDate date111 = LocalDate.parse(data111, formatter);
                    System.out.println("Indique o valor de N:");
                    int n11 = input.nextInt();
                    input.nextLine();
                    List<Long> res11 = model.mostUsedBestRep(n11, date11, date111);
                    for (Long r : res11) {
                        System.out.println("Id:" + r);
                    }
                    break;

                case "S":
                    break;
                default:
                    System.out.println("Opcão Inválida !");
                    break;
            }
        } while (!opcao.equals("S"));
    }
}
