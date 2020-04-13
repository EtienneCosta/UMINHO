import java.io.Serializable; 
/**
 * Subclasse de Actividade: Educação
 */
public class Educacao extends Actividade implements Serializable{
    /** Variáveis de Instância*/
    private static final double maximo = 250;
    private static final String descricao = "Educação";
    private static final double coeficiente = 0.4;
     
    /** Construtor por Omissão*/ 
    public Educacao(){
        super(maximo, descricao, coeficiente);
    }
    
    /** Construtor de cópia*/
    public Educacao(Educacao e){
        super(e);
    }
    
    public Educacao clone(){
        return new Educacao(this);
    }
}