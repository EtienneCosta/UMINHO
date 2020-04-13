
/**
 * Classe Abstracta Contribuinte
 *
 * @author Pedro Machado nºA33524, Etienne Costa nº a76089, Joana Cruz nºa76270
 * @version 2017/2018
 */

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.io.Serializable; 

public abstract class Contribuinte implements Serializable {
    
    /** Variáveis de instância*/
    private String nif; //NIF do contribuinte
    private String email;//E-mail 
    private String nome;//Nome
    private String morada;//Morada
    private String password;//Password  
    private Set<Factura> facturas;//Lista de Facturas
    
    /** Construtor por omissão */
    public Contribuinte(){
       this.nif = "";
       this.email = "";
       this.nome = "";
       this.morada = "";
       this.password = "";
       this.facturas = new TreeSet<>(new ComparatorFactura());
    }
    
    /** Construtor parametrizado */                     
    public Contribuinte(String nif, String email, String nome, String morada, String password,Set <Factura> facturas){
        this.nif = nif;
        this.email = email;
        this.nome = nome;
        this.morada = morada;
        this.password = password;
        this.facturas = facturas.stream().map(Factura:: clone).collect(Collectors.toSet());
    }       

    /** Construtor de cópia.*/ 
    public Contribuinte(Contribuinte cont){
        this.nif = cont.getNif();
        this.email = cont.getEmail();
        this.nome = cont.getNome();
        this.morada = cont.getMorada();
        this.password = cont.getPassword();
        this.facturas = cont.getFacturas();
    }

    /** Métodos Get */
    public String getNif(){
        return this.nif;
    }
    
    public String getEmail(){
        return this.email;
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public String getMorada(){
        return this.morada;
    }
    
    public String getPassword(){
        return this.password;
    }
    
    public Set<Factura> getFacturas(){
        return this.facturas;

    }
        
    /** Métodos Set */
    public void setNif(String nif){
        this.nif = nif;
    }

    public void setEmail(String email){
        this.email = email;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
    
    public void setMorada(String morada){
        this.morada = morada;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public void setFacturas(Set<Factura> f){
        this.facturas=f;
    }

    /** Método toString */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("NIF: "); 
        sb.append(this.nif+"\n");
        sb.append("E-mail: "); 
        sb.append(this.email+"\n");
        sb.append("Nome: "); 
        sb.append(this.nome+"\n");
        sb.append("Morada: "); 
        sb.append(this.morada+"\n");
        sb.append("\n*****Facturas*****");
        if(this.facturas.size() == 0){
            sb.append("\nNão existem facturas associadas a este contribuinte");
        }
        else{
            for (Factura f : this.facturas) 
                  sb.append("\n" + f.toString());
        }

        return sb.toString();
    }
    
    /** Método Equals */
    public boolean equals (Object o){
        if(this==o)
            return true;
         else
         if(o==null||o.getClass()!=this.getClass())
            return false;
        Contribuinte c=(Contribuinte) o;
         
        return(this.nif.equals(c.getNif()) &&
               this.email.equals(c.getEmail()) &&
               this.nome.equals(c.getNome()) &&
               this.morada.equals(c.getMorada()) &&
               this.password.equals(c.getPassword()))&&
               this.facturas.equals(c.getFacturas());
    }
    
    /** Assinatura do Método Clone a ser implementado*/
    public abstract Contribuinte clone();
    
    
    /** Calcula o gasto total de um individual*/
    public double totalValor(){
       return this.facturas.stream().mapToDouble(Factura::getValor).sum();
    }
    
    

}    

