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
public class Administrador extends Utilizador {
    
    public Administrador(){
        super();
    }
    
    public Administrador(String username, String password){
        super(username, password);
    }
    
    public Administrador(Administrador a){
        super(a);
    }
    
    @Override
    public Administrador clone(){
        return new Administrador(this);
    }
}
