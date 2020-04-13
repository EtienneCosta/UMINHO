import java.io.Serializable; 
/**
 * Subclasse de Actividade: Indefinida
 */
public class Indefinida extends Actividade implements Serializable
{
    /** Variáveis de Instância*/
    private static final double maximo = 0;
    private static final String descricao = "Indefinida";
    private static final double coeficiente = 0.0;
     
    /** Construtor por Omissão*/ 
    public Indefinida(){
        super(maximo, descricao, coeficiente);
    }
    
    /** Construtor de cópia*/
    public Indefinida(Indefinida i){
        super(i);
    }
    
    public Indefinida clone(){
        return new Indefinida(this);
    }
}