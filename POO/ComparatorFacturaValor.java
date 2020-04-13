import java.util.Comparator;
import java.io.Serializable;

public class ComparatorFacturaValor implements Comparator<Factura>,Serializable {

    public int compare(Factura f1, Factura f2){
        
        if(f1.equals(f2)) 
            return 0;
        
        else {
            if(f1.getNifCliente().compareTo(f2.getNifCliente()) == 0) 
                return 1;
            else
                 return Double.compare(f2.getValor(),f1.getValor());
            
    }

        }
}
