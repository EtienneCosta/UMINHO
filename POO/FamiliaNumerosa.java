
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

public class FamiliaNumerosa extends Individual implements Beneficiado,Serializable {
    
    /**Variáveis de instância da classe FamiliaNumerosa*/
    
    /**Número de dependentes no agregado familiar*/
    private int numDependentes;
    
    /** Construtor por omissão da classe FamiliaNumerosa*/
    public FamiliaNumerosa(){
        super();
        this.numDependentes=0;
    }
    
    /** Construtor parametrizado da classe FamiliaNumerosa*/ 
    public FamiliaNumerosa(String nif, String email, String nome, String morada, String password, int agregado, 
                     List <String> numsFiscais, double coef,List <Actividade> actividades,Set<Factura> f, int numDependentes){
        super(nif, email, nome, morada, password, agregado, numsFiscais, coef, actividades, f);
        this.numDependentes = numDependentes;
    }
    
    /** Construtor de cópia da classe FamiliaNumerosa*/
    public FamiliaNumerosa(FamiliaNumerosa f){
        super(f);
        this.numDependentes=f.getNumDependentes();
    }
    
    /**Getters*/
    
    /**Método que devolve o número de dependentes do agregado familiar*/
    public int getNumDependentes(){
        return this.numDependentes;
    }
    
    /**Setters*/
    
    /**Método que atribui o número de dependentes do agregado familiar.*/
    
    public void setNumDependentes(int numDependentes){
        this.numDependentes=numDependentes;
    }
    
    
    /**Método clone */
    
    public FamiliaNumerosa clone(){
        return new FamiliaNumerosa(this);
    }
    
    public boolean equals(Object o){
        if(this==o)
        return true;
       else 
        if(o==null||this.getClass()!=o.getClass())
         return false;
       FamiliaNumerosa f =(FamiliaNumerosa) o;
       
       return (super.equals(f) &&
               this.numDependentes==f.getNumDependentes());
            }
    
    /** Método toString */
    public String toString(){
     StringBuilder sb = new StringBuilder();
     sb.append(super.toString());
     sb.append("Número de dependentes: ");
     sb.append(this.numDependentes+"\n");
     
     return sb.toString();
    }
     
     
    /** Método que reduz os impostos as famílias numerosas.*/
   
    public double reducaoImposto(){
        double reducao = 1.0;
        reducao += this.numDependentes * 0.05; 
        return reducao;
    }
    
}
