/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import engine.User;
import java.util.Comparator;

/**
 * Comparator para ordenar users conforme o número de posts que efetuaram. Esta ordenação é descrescente.
 * @author joanacruz
 */
public class ComparatorNumberOfPost implements Comparator<User>{
    
    public int compare(User u1, User u2){
        int nPosts1 = u1.getPosts().size();
        int nPosts2 = u2.getPosts().size();
        if(Integer.compare(nPosts2, nPosts1) == 0) 
            return Long.compare(u2.getIduser(), u1.getIduser());
        else
            return Integer.compare(nPosts2, nPosts1);
       
    }
}
