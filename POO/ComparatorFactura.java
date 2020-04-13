import java.util.Comparator;
import java.io.Serializable;

public class ComparatorFactura implements Comparator<Factura>,Serializable {

    public int compare(Factura f1, Factura f2){
        
        if(f1.equals(f2)) 
            return 0;
        
        else {
            if(f1.getData().compareTo(f2.getData()) == 0) 
                return 1;
            else
               return f1.getData().compareTo(f2.getData());
            
    }

   }
}