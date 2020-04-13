/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author Joana Marta Cruz
 */
public class PasswordInvalidaException extends Exception{
    public PasswordInvalidaException() {
        super();
    }
    
    public PasswordInvalidaException(String s) {
        super(s);
    }
}
