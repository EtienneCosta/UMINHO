import java.io.Serializable; 
/**
 * Subclasse de Actividade: Transportes
 */
public class Transportes extends Actividade implements Serializable {
    /** Variáveis de Instância*/
    private static final double maximo = 120;
    private static final String descricao = "Transportes";
    private static final double coeficiente = 0.6;
     
    /** Construtor por Omissão*/ 
    public Transportes(){
        super(maximo, descricao, coeficiente);
    }
    
    /** Construtor de cópia*/
    public Transportes(Transportes t){
        super(t);
    }
    
    public Transportes clone(){
        return new Transportes(this);
    }
}