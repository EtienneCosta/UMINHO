import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.io.Serializable;

public class Individual extends Contribuinte implements Serializable{
   /**
    *  Variáveis de instância da classe Contribuinte.
    */
   /**NumAgregado:Número de dependentes do agregado familiar*/
   private int numAgregado;
   
   /**NumsFiscais:Números fiscais do agregado familiar;*/
   private List <String> numsFiscais;
   
   /**CoeficienteFiscal:Coeficiente fiscal para efeitos de dedução 
    * (um factor multiplicativo que é associado a cada despesa elegível);
    */
   private double coeficienteFiscal;
   
   /**Actividades: Listas das actividades económicas para as quais
    * um determinado contribuinte tem possibilidade de deduzir despesas.
    */
   private List<Actividade> actividades;

   /** Construtor por Omissão da classe Individual */
   public Individual(){
       super();
       this.numAgregado = 0;
       this.numsFiscais = new ArrayList<>();
       this.coeficienteFiscal = 0;
       this.actividades = new ArrayList<>();
   }


   /** Construtor Parametrizado da classe Individual */
   public Individual(String nif, String email, String nome, String morada, String password, int agregado,
                     List <String> numsFiscais, double coef,List <Actividade> actividades,Set<Factura> f){
       super(nif,email,nome,morada,password,f);
       this.numAgregado = agregado;
       this.coeficienteFiscal = coef;
       setNumerosFiscais(numsFiscais);
       setActividades(actividades);
   }

   /** Construtor de Cópia da classe Individual */
   public Individual(Individual i){
       super(i);
       this.numAgregado = i.getAgregado();
       this.numsFiscais = i.getNumsFiscais();
       this.coeficienteFiscal = i.getCoeficiente();
       this.actividades = i.getActividades();
   }

    /**
     * Getters
     */
  
   /** Método que devolve o número de dependentes do agregado familiar */
   public int getAgregado(){
       return this.numAgregado;
   }
   
   /** Método que devolve a lista dos números de identificação fiscal do agregado familiar */
   
   public List<String> getNumsFiscais(){
       List<String> nums = new ArrayList<>();
       for(String s: numsFiscais){
           nums.add(s);
       }
       return nums;
   }
   
   /** Método que devolve o coeficiente fiscal para efeitos de dedução */
   
   public double getCoeficiente(){
       return this.coeficienteFiscal;
   }
   
   /** Método que devolve a lista de actividades económicas na qual pode ser deduzida as despesas*/
   
   public List<Actividade> getActividades(){
      List<Actividade> res = new ArrayList <Actividade> ();
      for(Actividade a: this.actividades)
      res.add(a);
       return res;
   }
   
   /** Método que devolve o conjunto de facturas com mais actividades */
   
   public Set<Factura> getFacturasActs(GestaoContribuintes gc){
       Set<Factura> facturas = super.getFacturas();
       Set<Factura> facturasFiltradas = new TreeSet<>(new ComparatorFactura());

       for(Factura f : facturas){
           String nifEmitente = f.getNifEmitente();
           try{
               Empresa e = (Empresa) gc.getContribuinte(nifEmitente);
               if(e.maisActividade()){
               facturasFiltradas.add(f);
           }
           }
           catch (ContribuinteInexistenteException ex) {
               System.out.println("Contribuinte " + ex.getMessage() + " não existe!!!");
           }
       }

       return facturasFiltradas;
   }
   
   /**
    *  Setters
    */
   
   /** Método que atribui um número  */
   public void setNumAgregado(int num){
       this.numAgregado = num;
   }
   
 /** Método que atribui os números de identificação fiscal aos dependentes do agregado familiar */
 
   public void setNumerosFiscais(List<String> numsFiscais){
       this.numsFiscais = new ArrayList<>();
       numsFiscais.forEach(n->{this.numsFiscais.add(n);});
   }

   /** Método que atribui uma valor ao coeficiente fiscal para efeitos de dedução */
   
   public void setCoeficientes(double coeficiente){
       this.coeficienteFiscal = coeficiente;
   }
   
   /** Método que atribui as actividades económicas na qual se podem deduzir despesas*/
   
   public void setActividades(List<Actividade> actividades){
       this.actividades = new ArrayList<>();
       actividades.forEach(c->{this.actividades.add(c);});
   }

   /** Método toString */
   public String toString(){
       StringBuilder sb = new StringBuilder();
       sb.append("Número elementos agregado: ");
       sb.append(this.numAgregado+"\n");
       sb.append("Números fiscais associados: ");
       sb.append(numsFiscais.toString()+"\n");
       sb.append("Coeficiente fiscal: ");
       sb.append(this.coeficienteFiscal+"\n");
       sb.append("Actividades: ");   
       for(Actividade a : this.actividades)
       sb.append(a.getDescricao() +"\t");
       sb.append("\n"); 
       sb.append(super.toString());
       return sb.toString();
   }

   /** Método Equals */
   public boolean equals(Object o){
       if (this == o) return true;
       if ((o == null) || (this.getClass() != o.getClass())) return false;
       Individual i = (Individual) o;
       return(super.equals(i) &&
              this.numAgregado == i.getAgregado() &&
              this.numsFiscais.equals(i.getNumsFiscais()) &&
              this.coeficienteFiscal == i.getCoeficiente() &&
              this.actividades.equals(i.getActividades()));
   }

   /** Método Clone */
   public Individual clone(){
       return new Individual(this);
   }

   
   /** Método que devolve uma factura dado o seu número */
   public Factura getFactura(String numero) throws FacturaInexistenteException{
        Set<Factura> facturas = super.getFacturas();
        for(Factura f: facturas){
            if(numero.equals(f.getNumero())) 
            return f;
        }

        throw new FacturaInexistenteException(numero);
   }

   
   /** Método que gera as deduçoes por actividade no contribuinte individual*/
   public List<Actividade> geraActividades(){
       Set<Factura> facturas = super.getFacturas();
       Actividade a;
       for(Factura f : facturas){
          if(f.getValidada() ){
            f.getActividade().setAcumulado(0);
            actividades = addFacturaAct(f);
          }
       }
       return actividades;
    }
   
   /** Método que adiciona o valor de uma factura a um sector de actividade */
   public List<Actividade> addFacturaAct(Factura f){
       Set<Factura> facturas = super.getFacturas();
       Actividade a;
       
       if(!this.actividades.contains(f.getActividade())){  
                a = f.getActividade();
                this.actividades.add(a);
                a.deduzFactura(this.coeficienteFiscal*f.getValor());
            }
            else{
                int index = this.actividades.indexOf(f.getActividade());
                a = this.actividades.get(index);
                a.deduzFactura(this.coeficienteFiscal*f.getValor());
            }
       
       return actividades;
   }
   /**
    * Método que remove a dedução feita de uma respectiva factura.
    */
   public List<Actividade> removeFacturaAct(Factura f){
       Set<Factura> facturas = super.getFacturas();
       Actividade a;
       
       if(!this.actividades.contains(f.getActividade())){  
                a = f.getActividade();
                this.actividades.add(a);
                a.deduzFactura(this.coeficienteFiscal*f.getValor()*-1);
            }
            else{
                int index = this.actividades.indexOf(f.getActividade());
                a = this.actividades.get(index);
                a.deduzFactura(this.coeficienteFiscal*f.getValor()*-1);
            }
       
       return actividades;
   }
}
