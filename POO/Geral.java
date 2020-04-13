import java.io.Serializable; 

public class Geral extends Actividade implements Serializable{
    /** Variáveis de Instância*/
    private static final int maximo = 150;
    private static final String descricao = "Despesas Gerais";
    private static final double coeficiente = 0.25;
     
    /** Construtor por Omissão*/ 
    public Geral(){
        super(maximo, descricao, coeficiente);
    }
    
    /** Construtor de cópia*/
    public Geral(Geral s){
        super(s);
    }
    
    public Geral clone(){
        return new Geral(this);
    }
}