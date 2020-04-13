/**
*   Subclasse Empresa
*/

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Iterator;
import java.io.Serializable;

public class Empresa extends Contribuinte implements Serializable{
    /** Variáveis de Instância da classe Empresa*/
    
    /**Lista de actividades económicas na qual uma empresa participa*/
    private List<Actividade> actividades;
    
    /**Factor: factor que a empresa tem no cálculo de dedução fiscal */
    private double factor;
    
    /**
   * Gestão do map de comparadores.
   * A classe Empresa guarda os diferentes comparadores que as instâncias 
   * desta classe podem invocar para ordenar as suas instâncias de empresa.
   * */
    private static Map<String,Comparator<Factura>> comparadores = new HashMap<>();//Map de Comparadores

    /** Construtor por Omissão da classe Empresa */
    public Empresa(){
        super();
        this.actividades = new ArrayList<>();
        this.factor = 0.0;
    }

    /** Construtor Parametrizado da classe Empresa */
    public Empresa(String nif,String email,String nome,String morada,String password,
                List <Actividade> actividades, double factor,Set<Factura> f){
        super(nif,email,nome,morada,password,f);
        this.factor = factor;
        setActividades(actividades);
    }

    /** Construtor de Cópia da classe Empresa */
    public Empresa(Empresa e){
        super(e);
        this.actividades = e.getActividades();
        this.factor = e.getFactor();
    }
    
    /**
     * Getters
     */

    /** Método que deovolve a lista de actividades de uma respectiva empresa.*/
    public List<Actividade> getActividades(){
        List<Actividade> actividades = new ArrayList<>();
        for(Actividade a: this.actividades){
            actividades.add(a);
        }
        return actividades;
    }
    
    /** Método que devolve o factor que a empresa tem no cálculo de dedução fiscal. */
    public double getFactor(){
        return this.factor;
    }
    
    /**
     * Setters.
     */

    /** Método que atribui  as actividades a uma respectiva empresa. */
    public void setActividades(List<Actividade> actividades){
        this.actividades = new ArrayList<>();
        actividades.forEach(a->{this.actividades.add(a);});
    }
    
    /** Método que atribui o factor para efeitos de dedução fiscal de uma empresa.*/
    public void setFactor(double factor){
        this.factor = factor;
    }

    /** Método Clone */
    public Empresa clone(){
        return new Empresa(this);
    }

    /** Método toString */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Actividades: ");   
        for(Actividade a : this.actividades)
        sb.append(a.getDescricao() +"\t");
        sb.append("\n");        
        sb.append("Factor: ");
        sb.append(this.factor+"\n");
        sb.append(super.toString());
        return sb.toString();
    }

    /** Método Equals */
    public boolean equals(Object o){
        if(this == o)
            return true;
        else
        if ((o == null) || (this.getClass() != o.getClass()))
            return false;
        Empresa e = (Empresa) o;
        return(super.equals(e) &&
               this.factor == e.getFactor() &&
               this.actividades.equals(e.getActividades()));
        }
        
   /**
   * Gestão do map de comparadores.
   * A classe Empresa guarda os diferentes comparadores que as instâncias
   * desta classe podem invocar para ordenar as suas facturas.
   * */


   public static void juntaOrdenacao(String nome, Comparator<Factura> ordem){
        comparadores.put(nome, ordem);
   }

   public static Comparator<Factura> getOrdem(String nome) {
        return comparadores.get(nome);
   }

   public TreeSet<Factura> ordenarFacturas(Comparator<Factura> c) {
         TreeSet<Factura> t = new TreeSet<Factura>(c);
         super.getFacturas().forEach(h -> { t.add(h.clone());});
         return t;
   }

    public Iterator<Factura> ordenarFacturas(String criterio) {
         TreeSet<Factura> r = ordenarFacturas(getOrdem(criterio));
         return r.iterator();
    }


    /** 
     * Método que obtem por parte das empresas, as listagens das facturas por contribuinte
     * num intervalo de datas.*/
     
    public Set<Factura> listFacturasContribuinteDate(LocalDate begin,LocalDate end){
      Set<Factura> factura = new TreeSet<Factura>(new ComparatorFactura());
      LocalDateTime date1 = LocalDateTime.of(begin, LocalTime.of(0,0));
      LocalDateTime date2 = LocalDateTime.of(end, LocalTime.of(23,59));
      for(Factura f : super.getFacturas()){
           if(f.getData().isAfter(date1) && f.getData().isBefore(date2))
               factura.add(f);
       }

       return factura;
   }

   /** Método que calcula o valor faturado por uma empresa num intervalo de tempo.*/
   public double totalFacturadoDate(LocalDate begin,LocalDate end){
       LocalDateTime date1 = LocalDateTime.of(begin, LocalTime.of(0,0));
       LocalDateTime date2 = LocalDateTime.of(end, LocalTime.of(23,59));
       return super.getFacturas().stream().filter((f) -> f.getData().isAfter(date1) && f.getData().isBefore(date2)).mapToDouble(Factura::getValor).sum();
   }

   /** Método que verifica se uma empresa tem mais de uma actividade. */
   public boolean maisActividade(){
       if(this.actividades.size() > 1) return true;
       else return false;
   }


}
