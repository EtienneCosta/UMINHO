/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dssconfigurator.camadaLogica;

/**
 *
 * @author Joana Marta Cruz
 */
public abstract class Utilizador {
    private String username;
    private String password;
    
    public Utilizador(){
        this.username = "";
        this.password = "";
    }

    public Utilizador(String username, String password){
        this.username = username;
        this.password = password;
    }
    
    public Utilizador(Utilizador u){
        this.username = u.getUsername();
        this.password = u.getPassword();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public abstract Utilizador clone();
}
