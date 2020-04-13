/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dssconfigurator.camadaLogica;

import dssconfigurator.camadaDados.ComponenteDAO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Joana Marta Cruz
 */
public class Pacote {
    private String id;
    private String nome;
    private List<String> componentes;
    private double desconto;
    
    public Pacote(){
        this.id = "";
        this.nome = "";
        this.componentes = new ArrayList<>();
        this.desconto = 0;
    }
    
    public Pacote(String id, String nome, List<String> componentes, double desconto) {
        this.id = id;
        this.nome = nome;
        setComponentes(componentes);
        this.desconto = desconto;
    }
    public Pacote(Pacote p){
        this.id = p.getId();
        this.nome = p.getNome();
        this.componentes = p.getComponentes();
        this.desconto = p.getDesconto();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getComponentes() {
        List<String> res = new ArrayList<>();
        res.addAll(this.componentes);
        return res;
    }

    public void setComponentes(List<String> componentes) {
        this.componentes = new ArrayList<>();
        this.componentes.addAll(componentes);
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }
   

    @Override
    public Pacote clone(){
        return new Pacote(this);
    }
    
        @Override
    public boolean equals(Object o) {
        if(this==o)
            return true;
        else 
            if(o==null || this.getClass()!=o.getClass())
                return false;
        Pacote p = (Pacote) o;
        
        return (p.getId().equals(this.id));     
    }

}
