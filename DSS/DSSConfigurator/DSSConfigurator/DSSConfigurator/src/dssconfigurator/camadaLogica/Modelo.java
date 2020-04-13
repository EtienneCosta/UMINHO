/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dssconfigurator.camadaLogica;

import dssconfigurator.camadaDados.ComponenteDAO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Joana Marta Cruz
 */
public class Modelo {
    private String id;
    private String nome;
    private double preco;
    private Map<Categoria, List<String>> compDisponiveis;
    private List<String> pacotesDisponiveis;
    private ComponenteDAO componenteDAO;
    
    public Modelo(){
        this.id = "";
        this.nome = "";
        this.preco = 0;
        this.compDisponiveis = new HashMap<>();
        this.pacotesDisponiveis = new ArrayList<>();
        this.componenteDAO = new ComponenteDAO();
    }

    public Modelo(String id, String nome, double preco, Map<Categoria, List<String>> compDisponiveis, List<String> pacotes) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        setCompDisponiveis(compDisponiveis);
        setPacotesDisponiveis(pacotes);
    }
    
    public Modelo(Modelo m){
        this.id = m.getId();
        this.nome = m.getNome();
        this.preco = m.getPreco();
        this.compDisponiveis = m.getCompDisponiveis();
        this.pacotesDisponiveis = m.getPacotesDisponiveis();
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

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
    
    
    public Map<Categoria, List<String>> getCompDisponiveis() {
        
        Map<Categoria, List<String>> res = new HashMap<>();
        Categoria cat = null;
        
        for(List<String> lista : this.compDisponiveis.values()){
            List<String> aux = new ArrayList<>();
            for(String idComponente : lista){
                aux.add(idComponente);
                cat = this.componenteDAO.getCategoria(idComponente);
            }
            res.put(cat, aux);
        } 
        
        return this.compDisponiveis;
    }
    
    public void setCompDisponiveis(Map<Categoria, List<String>> compDisponiveis) {      
        this.compDisponiveis = new HashMap<>();
        Categoria cat = null;

        for(List<String> lista : compDisponiveis.values()){
            List<String> aux = new ArrayList<>();
            for(String idComponente : lista){
                aux.add(idComponente);
                cat = this.componenteDAO.getCategoria(idComponente);
            }
            this.compDisponiveis.put(cat, aux);
        } 
    }
    
    public List<String> getPacotesDisponiveis() {
        return this.pacotesDisponiveis;
    }

    public void setPacotesDisponiveis(List<String> pacotes) {
        this.pacotesDisponiveis = pacotes;
    }
    
    public List<String> getComponentesDisponiveisCategoria(Categoria c){
        return this.compDisponiveis.get(c);
    }
    
    
    public List<String> getAllOptionalComponentes(){
        List<String> componentes = new ArrayList<>();
        Categoria c= null;
        String idComponente = null;
        
        for(List<String> lista : this.compDisponiveis.values()){
            idComponente = lista.get(0);
            c = componenteDAO.getCategoria(idComponente);
            
            if(c == Categoria.MOTOR || c == Categoria.TRANSMISSÃO || 
            c == Categoria.COR || c == Categoria.ESTOFO || c == Categoria.CORTEJADILHO || c == Categoria.JANTES);
            else{
                componentes.addAll(lista);       
            }
        }  
        return componentes; 
    }
    
    @Override
    public Modelo clone(){
        return new Modelo(this);
    }

   
    
}
