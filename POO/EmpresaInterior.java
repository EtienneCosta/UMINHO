import java.io.Serializable;
import java.util.Set;
import java.util.List;

public class EmpresaInterior extends Empresa implements Beneficiado,Serializable {
    
    /**Construtor por omissão da classe EmpresaInterior*/
    public EmpresaInterior(){
        super();
    }
    
    /** Construtor parametrizado da classe EmpresaInteriror*/
    public EmpresaInterior(String nif, String email, String nome, String morada, String password,
                            List <Actividade> actividades, double factor,Set<Factura> f){
        super(nif, email, nome, morada, password, actividades, factor, f);
    }

    /** Construtor de cópia da classe EmpresaInterior*/
    public EmpresaInterior(EmpresaInterior e){
        super(e);
    }
    
    /**Método equals*/
    public boolean equals (Object o ){
        if(this==o)
        return true;
       else
        if(o==null|this.getClass()!=o.getClass())
        return false;
      EmpresaInterior e =(EmpresaInterior) o;
      
      return super.equals(e);  
    }
    
    /**Método toString*/
    public String toString(){
        return super.toString();
    }
    
    /** Método clone */
    public EmpresaInterior clone(){
        return new EmpresaInterior(this);
    }
    
    /**Método que reduz os imposto a uma empresa do interior */
    public double reducaoImposto(){
        return 1.15;
    }
}
