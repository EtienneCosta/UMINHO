import java.io.Serializable; 
/**
 * Subclasse de Actividade: Restauração
 */
public class Restauracao extends Actividade implements Serializable {
    /** Variáveis de Instância*/
    private static final double maximo = 100;
    private static final String descricao = "Restauração";
    private static final double coeficiente = 0.25;
     
    /** Construtor por Omissão*/ 
    public Restauracao(){
        super(maximo, descricao, coeficiente);
    }
    
    /** Construtor de cópia*/
    public Restauracao(Restauracao r){
        super(r);
    }
    
    public Restauracao clone(){
        return new Restauracao(this);
    }
    
}