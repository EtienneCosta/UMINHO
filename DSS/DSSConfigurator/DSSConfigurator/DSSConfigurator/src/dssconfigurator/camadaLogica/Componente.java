/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dssconfigurator.camadaLogica;

import dssconfigurator.camadaDados.ComponenteDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



/**
 *
 * @author joanacruz
 */
public class Componente implements Comparable<Componente>{
    private String id;
    private String nome;
    private String descrição;
    private Categoria categoria;
    private double preco;
    private int stock;
    private List<String> incompativeis;
    private List<String> dependentes;
    private ComponenteDAO componenteDAO;


    public Componente(){
        this.id = "";
        this.nome = "";
        this.descrição = "";
        this.categoria = Categoria.NONE;
        this.preco = 0;
        this.stock = 0;
        this.incompativeis = new ArrayList<>();
        this.dependentes = new ArrayList<>();
        this.componenteDAO = new ComponenteDAO();
    }

    public Componente(String id, String nome, String descrição, Categoria categoria, double preco, int stock, List<String> incompativeis, List<String> dependentes){
        this.id = id;
        this.nome = nome;
        this.descrição = descrição;
        this.categoria = categoria;
        this.preco = preco;
        this.stock = stock;
        setIncompativeis(incompativeis);
        setDependentes(dependentes);
    }

    public Componente(Componente c) {
        this.id = c.getId();
        this.nome = c.getNome();
        this.descrição = c.getDescrição();
        this.categoria = c.getCategoria();
        this.preco = c.getPreco();
        this.stock = c.getStock();
        this.incompativeis = c.getIncompativeis();
        this.dependentes = c.getDependentes();
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
    public String getDescrição() {
        return descrição;
    }

    public void setDescrição(String descrição) {
        this.descrição = descrição;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    public List<String> getIncompativeis() {
        List<String> res = new ArrayList<>();
        res.addAll(this.incompativeis);
        return res;
    }

    public void setIncompativeis(List<String> incompativeis) {
        this.incompativeis = new ArrayList<>();
        this.incompativeis.addAll(incompativeis);
    }

    public List<String> getDependentes() {
        List<String> res = new ArrayList<>();
        res.addAll(this.dependentes);
        return res;
    }

    public void setDependentes(List<String> dependentes) {
        this.dependentes = new ArrayList<>();
        this.dependentes.addAll(dependentes);
    }
 
    @Override
    public Componente clone(){
        return new Componente(this);
    }
    
    public String getNameAndPrice(){
        return (this.nome + " : " + this.preco + " €");
    }
    
    public String getIdAndName(){
        return (this.id + " : " + this.nome);
    }
    
    @Override
    public boolean equals(Object o) {
        if(this==o)
            return true;
        else 
            if(o==null || this.getClass()!=o.getClass())
                return false;
        Componente c = (Componente) o;
        
        return (c.getId().equals(this.id) &&
               c.getNome().equals(this.nome) &&
               c.getDescrição().equals(this.descrição) &&
               c.getPreco() == (this.preco) &&
               c.getStock() == (this.getStock()) &&
               c.getCategoria().equals(this.getCategoria()));     
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.id);
        hash = 73 * hash + Objects.hashCode(this.nome);
        hash = 73 * hash + Objects.hashCode(this.descrição);
        hash = 73 * hash + Objects.hashCode(this.categoria);
        hash = 73 * hash + (int) (Double.doubleToLongBits(this.preco) ^ (Double.doubleToLongBits(this.preco) >>> 32));
        hash = 73 * hash + this.stock;
        hash = 73 * hash + Objects.hashCode(this.incompativeis);
        hash = 73 * hash + Objects.hashCode(this.dependentes);
        return hash;
    }
    
    
    public double getPrecoDependentes(){
        double soma = 0;
        
        soma = this.dependentes.stream().map((s) -> this.componenteDAO.getPreco(s)).reduce(soma, (accumulator, _item) -> accumulator + _item);         
        
        return soma;
    }
    
    
    public int compareTo(Componente c){
        double preco = this.preco + this.getPrecoDependentes();
        double preco1 = c.getPreco() + c.getPrecoDependentes();
        int res;
        
        if(this.equals(c)) return 0;
        
        if(preco == preco1){
            res = 1;
        }
        else if(preco > preco1){
            res = 1;
        }
        else res = -1;
        
        return res;
    }
    
}
