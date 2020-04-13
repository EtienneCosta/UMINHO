/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

/**
 *
 * @author joanacruz
 */
public class Opcao {
    //variáveis de instância
    private String texto;
    private String opcao;
    
    //Construtor parametrizado
    public Opcao(String texto, String opcao){
        this.texto = texto;
        this.opcao = opcao;
    }
    
    // Método toString
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.texto).append(this.opcao);
        
        return sb.toString();
    }
}
