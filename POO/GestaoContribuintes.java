import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.Serializable;


public class GestaoContribuintes implements Serializable {
    
    /** Variáveis de instância da classe GestaoContribuintes */
    
    /** Map -> Key : Nif de um contribuinte.
     *         Value: Contribuinte. 
     */
    private Map <String,Contribuinte> contribuintes;
  
    /** Construtor por Omissão*/
    public GestaoContribuintes(){
        this.contribuintes = new HashMap<>();
    }
    
    /** Método que devolve um contribuinte dado o seu número de identificação fiscal. */
    
    public Contribuinte getContribuinte(String nif) throws ContribuinteInexistenteException{
        Contribuinte c;
        
        if(!this.contribuintes.containsKey(nif)) {
            throw new ContribuinteInexistenteException(nif);
        }
        
        c = this.contribuintes.get(nif);
        return c;
    }
    
    /** Método toString */
    public String toString(){
        StringBuffer sb = new StringBuffer();
        for(Contribuinte c: this.contribuintes.values())
            sb.append(c.toString() + "\n");
        return sb.toString();
    }
        
    /**Método que regista um Contribuinte ao sistema.*/
    public void registaContribuinte(Contribuinte c) throws ContribuinteExistenteException{
        
        if(this.contribuintes.containsKey(c.getNif())){
            throw new ContribuinteExistenteException(c.getNif());
        }
        
        contribuintes.put(c.getNif(),c);
    }
    
    /**Método que valida o acesso à aplicação */
    
    public boolean login(String Nif,String Password){
        if(this.contribuintes.containsKey(Nif)&&
           contribuintes.get(Nif).getPassword().equals(Password))
             return true;
            else return false;
        }
        
  
   /**
    * Método que cria facturas associadas a um contribuinte indivual geradas por
    * uma empresa.
    */
   public Set<Factura> criaFactura(Contribuinte c){
       String nif = c.getNif();
       Set<Factura> factura = new TreeSet<Factura>(new ComparatorFactura());
       
       for(Contribuinte empresa: this.contribuintes.values())
           if(empresa instanceof Empresa){
           for(Factura f : empresa.getFacturas())
             if(nif.equals(f.getNifCliente()))
               factura.add(f);
            }
       return factura;
   }

   /** Método que retorna os 10 contribuintes que mais gastam.*/
   public Set<Contribuinte> maisGastam(){
       Set<Contribuinte> individuais = new TreeSet<Contribuinte>(new ComparatorContribuinteValor());
       Set<Contribuinte> res = new TreeSet<Contribuinte>(new ComparatorContribuinteValor());
       
       for(Contribuinte individual: this.contribuintes.values())
          if(individual instanceof Individual){
             individuais.add(individual);
          }
       
       int i=10; 
       for(Contribuinte c : individuais)
            if(i>=0){

                res.add(c);
                i--;
            }       

       return res;
   }
   
   /** Método que retorna as n empresas que mais faturam*/
   public Set<Contribuinte> maisFacturam(int n){
       Set<Contribuinte> empresas = new TreeSet<Contribuinte>(new ComparatorContribuinteValor());
       Iterator itTreeSet = empresas.iterator();
       Set<Contribuinte> res = new TreeSet<Contribuinte>(new ComparatorContribuinteValor());
       
       for(Contribuinte empresa : this.contribuintes.values())
          if(empresa instanceof Empresa){
             empresas.add(empresa);
          }
        
       for(Contribuinte c : empresas)
            if(n>=0){

                res.add(c);
                n--;
            } 
       
       return res;
   }
   
   /** Método que calcula as deduções das n empresas que mais facturam. */ 
   public List<Double> calculoDeducaoMaisFacturam(int n){
       Set<Contribuinte> empresas = maisFacturam(n);
       List<Double> res = new ArrayList<>();
       
       empresas.stream().forEach(c -> res.add(c.getFacturas().stream().mapToDouble(Factura::calculoDeducao).sum()));
       
       return res;
   }

    /**
     * Método que guarda em ficheiro de objectos o objecto que recebe a mensagem.
     */ 
    public void guardaEstado (String nomeFicheiro) throws FileNotFoundException,IOException{
        FileOutputStream fos = new FileOutputStream (nomeFicheiro);
        ObjectOutputStream oos = new ObjectOutputStream (fos);
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }
    
    
    /** 
    * Método que recupera uma instância de GestãoContribuinte de um ficheiro
    * de objectos.
    * Este método tem de ser um método de classe que devolva uma instância
    * já construída de GestãoContribuinte.
    */ 
   public static GestaoContribuintes carregaEstado(String nomeFicheiro) throws FileNotFoundException,IOException,ClassNotFoundException {
       
       FileInputStream fis = new FileInputStream(nomeFicheiro);
       ObjectInputStream ois = new ObjectInputStream (fis);
       GestaoContribuintes g = (GestaoContribuintes) ois.readObject();
       ois.close();
       return g;
   }
}


    

