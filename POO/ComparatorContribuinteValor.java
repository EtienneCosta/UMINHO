import java.util.Comparator;
import java.io.Serializable;

public class ComparatorContribuinteValor implements Comparator<Contribuinte>,Serializable {
   
    public int compare(Contribuinte i1,Contribuinte i2){
         return Double.compare(i2.totalValor(),i1.totalValor());
    }
}
