/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dssconfigurator.camadaLogica;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Joana Marta Cruz
 */
public class FuncionarioStand extends Funcionario{
    
    public FuncionarioStand(){
        super();
    }

    public FuncionarioStand(String username, String password, String nome, String morada, String contacto){
        super(username, password, nome, morada, contacto);
    }
    
    public FuncionarioStand(FuncionarioStand f){
        super(f);
    }

    @Override
    public FuncionarioStand clone() {
        return new FuncionarioStand(this);
    }
}
