/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dssconfigurator.camadaLogica;

/**
 *
 * @author joanacruz
 */
public abstract class Funcionario extends Utilizador{
    
    private String nome;
    private String morada;
    private String contacto;

    public Funcionario(){
        super();
        this.nome ="";
        this.morada = "";
        this.contacto = "";
    }

    public Funcionario(String username, String password, String nome, String morada, String contacto){
        super(username, password);
        this.nome = nome;
        this.morada = morada;
        this.contacto = contacto;
    }
    
    public Funcionario(Funcionario f){
        super(f);
        this.nome = f.getNome();
        this.morada = f.getMorada();
        this.contacto = f.getContacto();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }
    
    public String getDepartamento(){
        if(this instanceof FuncionarioStand) return "Stand";
        else return "Fabrica";
    }
    
    @Override
    public abstract Funcionario clone();   

}
