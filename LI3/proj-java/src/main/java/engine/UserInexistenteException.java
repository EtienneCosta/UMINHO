/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

/**
 * Exceção para tratar erro sobre a inexistência de um User
 * @author joanacruz
 */
public class UserInexistenteException extends Exception{
    
    public UserInexistenteException(){
        super();
    }
    
    public UserInexistenteException(String message){
        super(message);
    }
}
