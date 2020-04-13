import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.io.Serializable; 

public class Factura implements Serializable {
    /**Variáveis de Instância da classe Factura*/
    
    /** Número da factura */
    private String numero; 
    /**Número de identificação fiscal do emitente */
    private String nifEmitente;
    /**Data de emissão de uma factura.*/
    private LocalDateTime dataFactura;
    /**Número de identificação do cliente.*/
    private String nifCliente;
    /**Descrição da factura */
    private String descricao;
    /**Actividade económica associada a factura.*/
    private Actividade actividade;
    /**Valor total pago na respectiva factura.*/
    private double valor;
    /**Valor associado a facturas validadas ou não.*/
    private boolean validada;
    /**Guarda todas as alterações efectuadas numa determinada factura*/
    private List<Actividade> registo;
    
    
    /**  Construtor por omissão da classe Factura */
    public Factura(){
        this.numero= "";
        this.nifEmitente = "";
        this.dataFactura = LocalDateTime.now();
        this.nifCliente = "";
        this.descricao = "";
        this.actividade = null;
        this.valor = 0;
        this.validada = false;
        this.registo = new ArrayList<>();
    }

    /** Construtor parametrizado da classe Factura */
    public Factura(String numero,String nif_emitente, LocalDateTime data_factura, String nif_cliente, 
                String descricao,Actividade actividade, double valor, boolean validada, List<Actividade> registo){
        this.nifEmitente = nif_emitente;
        this.numero = numero;
        this.dataFactura = data_factura;
        this.nifCliente = nif_cliente;
        this.descricao = descricao;
        this.actividade = actividade;
        this.valor = valor;
        this.validada = validada;
        setRegisto(registo);
    }

    /** Construtor de cópia da classe Factura */
    public Factura(Factura f) {
        this.numero = f.getNumero();
        this.nifEmitente = f.getNifEmitente();
        this.dataFactura = f.getData();
        this.nifCliente = f.getNifCliente();
        this.descricao = f.getDescricao();
        this.actividade = f.getActividade();
        this.valor = f.getValor();
        this.validada = f.getValidada();
        this.registo = f.getRegisto();
    }
    /**
     * Getters
     */
    
   
   /** Método que devolve o número de uma factura.*/
   
   public String getNumero(){
        return this.numero;
    }
   
   /** Método que devolve o número de identificação do emitente.*/
   
   public String getNifEmitente(){
        return this.nifEmitente;
    }

   /** Método que devolve a data de emissão da factura.*/
    
    public LocalDateTime getData(){
        return this.dataFactura;
    }
   
   /** Método que devolve o número de identificação fiscal do cliente*/
   
   public String getNifCliente(){
        return this.nifCliente;
    }
   
   /** Método que devolve a descrição de uma factura.*/
   
   public String getDescricao(){
         return this.descricao;
    }   
    
   /** Método que devolve a actividade económica de uma determinada factura.*/
   
   public Actividade getActividade(){
        return this.actividade;
    }
   
   /** Método que devolve o valor */ 
   public double getValor(){
        return this.valor;
    }

    public boolean getValidada(){
        return this.validada;
    }
    
    public List<Actividade> getRegisto(){
        return this.registo.stream().map(Actividade::clone).collect(Collectors.toList());
    }
    
    /** Métodos Set */
    public void setNifEmitente(String nif_emitente){
        this.nifEmitente = nif_emitente;
    }
    
    public void setNumero(String numero){
        this.numero = numero;
    }
                                
    public void setData(LocalDateTime data_factura){
        this.dataFactura = data_factura;
    }
                                
    public void setNifCliente(String nif_cliente){
        this.nifCliente = nif_cliente;
    }
 
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }
                    
    public void setActividade(Actividade actividade){
        this.actividade = actividade;
    }
                                 
    public void setValor(double valor){
        this.valor = valor;
    }
    
    public void setValidada(boolean validada){
        this.validada = validada;
    }

    public void setRegisto(List<Actividade> registo){
        this.registo = new ArrayList<>();
        registo.forEach((a) -> {this.registo.add(a);});
    }
    
    /** Método toString */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("*******************\n");
        sb.append("Número de Factura: ");
        sb.append(this.numero +"\n");
        sb.append("NIF Emitente: "); 
        sb.append(this.nifEmitente+"\n");
        sb.append("Data da Factura: "); 
        sb.append(this.dataFactura+"\n");
        sb.append("NIF Cliente: "); 
        sb.append(this.nifCliente+"\n");
        sb.append("Descricao: "); 
        sb.append(this.descricao+"\n");
        sb.append("Actividade: "); 
        sb.append(this.actividade +"\n");
        sb.append("Valor: "); 
        sb.append(this.valor+"\n");
        sb.append("Validada: ");
        sb.append(this.validada+"\n");
        sb.append("Historico de Actividades: ");
        sb.append(this.registo.toString() + "\n");
        return sb.toString();
        }
    
    /** Método Equals*/
    public boolean equals(Object o){
       if(this == o) 
       return true;
       else
       if(o == null || this.getClass() != o.getClass()) 
       return false;
       Factura f = (Factura) o;
       return (this.nifEmitente.equals(f.getNifEmitente()) &&
               this.dataFactura.equals(f.getData()) &&
               this.nifCliente.equals (f.getNifCliente()) && 
               this.descricao.equals (f.getDescricao()) &&
               this.actividade.equals (f.getActividade()) &&
               this.valor == f.getValor() &&
               this.numero.equals(f.getNumero()) &&
               this.validada == f.getValidada() &&
               this.registo.equals(f.getRegisto()));
        }
           
    /** Método Clone*/
    public Factura clone(){
        return new Factura(this);
    }
    
    public double calculoDeducao(){
        if(this.validada) {
            return this.actividade.deducaoMaxima(this.valor);
        }
        else return 0;
    }
    
}