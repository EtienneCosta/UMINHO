import java.io.Serializable; 
/**
 * Subclasse de Actividade: Saúde
 */
public class Saude extends Actividade implements Serializable{
    /** Variáveis de Instância*/
    private static final double maximo = 500;
    private static final String descricao = "Saúde";
    private static final double coeficiente = 0.6;
     
    /** Construtor por Omissão*/ 
    public Saude(){
        super(maximo, descricao, coeficiente);
    }
    
    /** Construtor de cópia*/
    public Saude(Saude s){
        super(s);
    }
    
    public Saude clone(){
        return new Saude(this);
    }
    
}
