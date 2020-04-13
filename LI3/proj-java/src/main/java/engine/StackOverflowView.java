/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author joanacruz
 */
public class StackOverflowView {
    
    private Menu menuStackOverflow = new Menu();
    
    public StackOverflowView(){
        menuStackOverflow = initView();
    }
    
    public Menu getStackOverflowView(){
        return this.menuStackOverflow;
    }
    
    public static Menu initView() {
        Opcao op1, op2, op3, op4, op5, op6, op7, op8, op9, op10, op11, op12; // melhor: ArrayList<Opcao>
        op1 = new Opcao("InfoFromPost............", "1");
        op2 = new Opcao("TopMostActive...........", "2");
        op3 = new Opcao("TotalPosts..............", "3");
        op4 = new Opcao("QuestionsWithTag........", "4");
        op5 = new Opcao("GetUserInfo.............", "5");
        op6 = new Opcao("MostVotedAnswers........", "6");
        op7 = new Opcao("MostAnsweredQuestions...", "7");
        op8 = new Opcao("ContainsWord............", "8");
        op9 = new Opcao("BothParticipated........", "9");
        op10 = new Opcao("BetterAnswer............", "10");
        op11 = new Opcao("MostUsedBestRep.........", "11");
        op12 = new Opcao("Sair....................", "S");


        List<Opcao> linhas = Arrays.asList(op1, op2, op3, op4, op5, op6, op7, op8, op9, op10, op11, op12);
        Menu menuQueries = new Menu(linhas, "Queries");

        return menuQueries;
    } 
}
