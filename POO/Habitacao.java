import java.io.Serializable; 
/**
 * Subclasse de Actividade: Transportes
 */
public class Habitacao extends Actividade implements Serializable
{
    /** Variáveis de Instância*/
    private static final double maximo = 300;
    private static final String descricao = "Habitacao";
    private static final double coeficiente = 0.5;
     
    /** Construtor por Omissão*/ 
    public Habitacao(){
        super(maximo, descricao, coeficiente);
    }
    
    /** Construtor de cópia*/
    public Habitacao(Habitacao o){
        super(o);
    }
    
    public Habitacao clone(){
        return new Habitacao(this);
    }
}