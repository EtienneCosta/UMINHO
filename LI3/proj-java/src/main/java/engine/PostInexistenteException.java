    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

/**
 * Exceção para tratar erro sobre a inexistência de um Post
 * @author joanacruz
 */
public class PostInexistenteException extends Exception{
    
    public PostInexistenteException(){
        super();
    }
    
    public PostInexistenteException(String message){
        super(message);
    }
}
