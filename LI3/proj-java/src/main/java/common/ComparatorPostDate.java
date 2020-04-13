package common;

import engine.Post;
import java.util.Comparator;



/**
 * Comparator para ordenar posts conforme a sua data de criação. Esta ordenação é descrescente.
 * @author Joana Marta Cruz
 */
public class ComparatorPostDate implements Comparator<Post>{
    public int compare(Post p1, Post p2){
        int res = p2.getCreationDate().compareTo(p1.getCreationDate());
        return res==0? 1 : res;
    }   
}
