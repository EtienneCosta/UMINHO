package engine;

import java.util.ArrayList;
import java.util.List;


public class Menu {
    private List<Opcao> linhas;
    private String nome;
    
    //Construtor por omissão
    public Menu(){
        this.linhas = new ArrayList<>();
        this.nome = "";
    }
    
    //Construtor parametrizado
    public Menu(List<Opcao> linhas, String nome){
        setLinhas(linhas);
        this.nome = nome;
    }
    
    //Setter
    public void setLinhas(List<Opcao> linhas){
        this.linhas = new ArrayList<>();
        linhas.forEach(o -> {this.linhas.add(o);});
    }
    
    
    public void show(){
        //StringBuffer sb = new StringBuffer();
        System.out.println("--------------------------------------------------------------------");
        System.out.println("                          " + this.nome);
        System.out.println("--------------------------------------------------------------------");
        for(Opcao o: this.linhas){
            System.out.println(o.toString());
        }
        System.out.println("--------------------------------------------------------------------");
        System.out.println("Introduza uma opção! ");
    }
}
