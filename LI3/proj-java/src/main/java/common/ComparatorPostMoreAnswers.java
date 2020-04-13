/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import engine.Post;
import java.util.Comparator;

/**
 * Comparator para ordenar posts conforme o número de respostas que têm. Esta ordenação é descrescente.
 * @author joanacruz
 */
public class ComparatorPostMoreAnswers implements Comparator<Post>{
    public int compare(Post p1, Post p2){
        if(Long.compare(p2.getAnswerCount(), p1.getAnswerCount()) == 0) 
           return Long.compare(p2.getId(), p1.getId());
        else
           return Long.compare(p2.getAnswerCount(), p1.getAnswerCount());
        
    }
}
