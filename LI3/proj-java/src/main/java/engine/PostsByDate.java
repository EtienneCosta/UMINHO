/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import engine.TreePair;
import engine.Post;
import engine.PostInexistenteException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author joanacruz
 */
public class PostsByDate {
    private Map<LocalDate, TreePair> postsByDate; 

    public PostsByDate(){
        this.postsByDate = new HashMap<>();
    }
    
    //Getter
    public Map<LocalDate, TreePair> getPostsByDate(){
        return this.postsByDate.entrySet()
                          .stream()
                          .collect(Collectors.toMap(e->e.getKey(), e->e.getValue().clone()));
       
    }
    
    //Setter
    public void setPostsByDate(Map<LocalDate, TreePair> posts) {
        this.postsByDate = posts.entrySet()
                            .stream()
                            .collect(Collectors.toMap(e->e.getKey(), e->e.getValue().clone()));
    }
   
    // Método que adiciona um post à estrutura de posts
    public void addPost(Post p){
        LocalDate dayOfPost = p.getCreationDate().toLocalDate();
        if(this.postsByDate.containsKey(dayOfPost)){
            TreePair postsOfDay = this.postsByDate.get(dayOfPost);
            if(p.isQuestion()){
                postsOfDay.addQuestion(p);
            }
            else if(p.isAnswer()){
                postsOfDay.addAnswer(p);
            }
        }
        else{
            TreePair postsOfDay = new TreePair();
            if(p.isQuestion()){
               postsOfDay.addQuestion(p);
            }
            else if(p.isAnswer()){
                postsOfDay.addAnswer(p);
            }
            this.postsByDate.put(dayOfPost, postsOfDay);
        }
       
    }
    
    //Método Clone
    public PostsByDate clone(){
        PostsByDate p = new PostsByDate();
        p.postsByDate  = this.postsByDate.entrySet()
                            .stream()
                            .collect(Collectors.toMap(e->e.getKey(), e->e.getValue().clone()));
        return p;
    }
    
    // Método que filtra os posts que foram feitos entre as datas begin e end
    public Map<LocalDate, TreePair> postsFilteredDate(LocalDate begin, LocalDate end){
        return this.postsByDate.entrySet().stream().
                    filter(a -> a.getKey().equals(begin) ||
                                a.getKey().equals(end) ||
                               (a.getKey().isAfter(begin) &&
                                a.getKey().isBefore(end))).
                    collect(Collectors.toMap(e->e.getKey(),e->e.getValue()));
    
    }
    
    // Método que retorna um post correpondente ao id fornecido
    public Post getPost(long id){
        Post p = new Post();
        
        for (TreePair pair : this.postsByDate.values()){
            p = pair.getPost(id);
            if(p != null)
                break;
        }
        return p;
    }
}
