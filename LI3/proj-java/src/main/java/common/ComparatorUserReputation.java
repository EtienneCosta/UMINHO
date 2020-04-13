/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import engine.User;
import java.util.Comparator;

/**
 * Comparator para ordenar users conforme a sua reputação. Esta ordenação é descrescente.
 * @author joanacruz
 */
public class ComparatorUserReputation implements Comparator<User> {
    public int compare(User u1, User u2){
        if(Long.compare(u2.getReputation(), u1.getReputation()) == 0) 
           return 1;
        else
           return Long.compare(u2.getReputation(), u1.getReputation());
    }
}
