/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.util.Comparator;

/**
 * Comparator para ordenar tags conforme o número de ocorrências. Esta ordenação é descrescente.
 * @author joanacruz
 */
public class ComparatorTagOcorrencias implements Comparator<Tag>{
    public int compare(Tag t1, Tag t2){
        if(Integer.compare(t2.getOcorrencia(), t1.getOcorrencia()) == 0) 
           return Long.compare(t1.getIdTag(), t2.getIdTag());
        else
           return Integer.compare(t2.getOcorrencia(), t1.getOcorrencia());
    }
}
