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
public class FuncionarioFabrica extends Funcionario{
    
    public FuncionarioFabrica(){
        super();
    }
    
    public FuncionarioFabrica(String username, String password, String nome, String morada, String contacto){
        super(username, password, nome, morada, contacto);
    }
    
    public FuncionarioFabrica(FuncionarioFabrica f){
        super(f);
    }
    
    @Override
    public FuncionarioFabrica clone() {
        return new FuncionarioFabrica(this);
    }
}
