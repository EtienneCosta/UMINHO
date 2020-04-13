/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dssconfigurator.camadaLogica;

import dssconfigurator.camadaDados.ComponenteDAO;
import dssconfigurator.camadaDados.PacoteDAO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Joana Marta Cruz
 */
public class Configuracao {
    
    private String idModelo;
    private String idFuncionario;
    private List<String> componentesBase;
    private List<String> componentesOpcionais;
    private List<String> pacotes;
    
    public Configuracao(){
        this.idModelo = "";
        this.idFuncionario = "";
        this.componentesBase = new ArrayList<>();
        this.componentesOpcionais = new ArrayList<>();
        this.pacotes = new ArrayList<>();
    }

    public Configuracao(String idModelo, String idFuncionario, List<String> componentesBase, List<String> componentesOpcionais, List<String> pacotes) {
        this.idModelo = idModelo;
        this.idFuncionario = idFuncionario;
        setComponentesBase(componentesBase);
        setComponentesOpcionais(componentesOpcionais);
        setPacotes(pacotes);
    }
    
    public Configuracao(Configuracao c) {
        this.idModelo = c.getIdModelo();
        this.idFuncionario = c.getIdFuncionario();
        this.componentesBase = c.getComponentesBase();
        this.componentesOpcionais = c.getComponentesOpcionais();
        this.pacotes = c.getPacotes();
    }

    public String getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(String idModelo) {
        this.idModelo = idModelo;
    }

    public List<String> getComponentesBase() {
        List<String> res = new ArrayList<>();
        res.addAll(this.componentesBase);
        return res;
    }

    public void setComponentesBase(List<String> componentesBase) {
        this.componentesBase = new ArrayList<>();
        this.componentesBase.addAll(componentesBase);
    }

    public List<String> getComponentesOpcionais() {
        List<String> res = new ArrayList<>();
        res.addAll(this.componentesOpcionais);
        return res;
    }

    public void setComponentesOpcionais(List<String> componentesOpcionais) {
        this.componentesOpcionais = new ArrayList<>();
        this.componentesOpcionais.addAll(componentesOpcionais);
    }

    public List<String> getPacotes() {
        List<String> res = new ArrayList<>();
        res.addAll(this.pacotes);
        return res;
    }

    public void setPacotes(List<String> pacotes) {
        this.pacotes = new ArrayList<>();
        this.pacotes.addAll(pacotes);
    }

    public String getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(String idFuncionario) {
        this.idFuncionario = idFuncionario;
    }
    
    public Configuracao clone(){
        return new Configuracao(this);
    }
    
    public void adicionaComponenteOpcional(String idComponente){
        this.componentesOpcionais.add(idComponente);
    }
    
    public void removeComponenteOpcional(String idComponente){
        this.componentesOpcionais.remove(idComponente);
    }
    
    public void adicionaPacote(String idPacote){
        this.pacotes.add(idPacote);
    }
    
    public void removePacote(String idPacote){
        this.pacotes.remove(idPacote);
    }
    
    
    public List<String> getComponentesBaseEOpcionais(){
        List<String> res = new ArrayList<>();
        
        res.addAll(this.componentesBase);
        res.addAll(this.componentesOpcionais);
        
        return res;
    }
    
}