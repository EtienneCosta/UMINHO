package engine; 

import common.ComparatorPostDate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;


public class User {
    
    /** Corresponde ao id de um utilizador */
    long idUser;
    /** Corresponde ao nome de um utilizador */
    String name;
    /** Corresponde a reputação de um utilizador */
    long reputation;
    /** Corresponde a biografia de um utilizador */ 
    String bio;
    /** Corresponde a uma árvore com todos os posts do utilizador*/
    Set<Post> posts;
    
    
    /****************
    * Construtores *
    ****************/
    //Construtor por omissão
    public User(){
        this.idUser=0;
        this.name="";
        this.reputation=0;
        this.bio="";
        this.posts = new TreeSet<>(new ComparatorPostDate());
    }
   
    //Construtor parametrizado
    public User(long idUser,String name,long reputation,String bio, Set<Post> posts){
        this.idUser=idUser;
        this.name=name;
        this.reputation=reputation;
        this.bio=bio;
        setPosts(posts);
    }
    
    //Construtor de cópia   
    public User(User u){
        this.idUser=u.getIduser();
        this.name=u.getName();
        this.reputation=u.getReputation();
        this.bio=u.getBio();
        this.posts = u.getPosts();
    }
    
    /****************
    *   Getters    *
    ****************/
    public long getIduser(){
        return this.idUser;
    }
    
    public String getName(){
        return this.name;
    }
    
    public long getReputation(){
        return this.reputation;
    }
    
    public String getBio(){
        return this.bio;
    }
    
    public Set<Post> getPosts(){
        Set<Post> res = new TreeSet<>(new ComparatorPostDate());
        for(Post p : this.posts){
            res.add(p);
        }
        return res;
    }
    
    /****************
    *    Setters    *
    ****************/
    public void setIduser(long idUser){
        this.idUser=idUser;
    }
    
    public void setName (String name){
        this.name=name;
    }
    
    public void setReputation (long reputation) {
        this.reputation=reputation;
    }
    
    public void setBio (String bio){
        this.bio=bio;
    }
    
    public void setPosts(Set<Post> posts){
        this.posts = new TreeSet<>(new ComparatorPostDate());
        posts.forEach(p-> {this.posts.add(p);});
    }
    
    /** Método Clone */ 
    public User clone(){
        return new User(this);
    }
    
    /** Método equals */   
    public boolean equals(Object o){
        if(o==this) return true;
        else if(o==null||o.getClass()!=this.getClass()) return false;
        User u=(User) o;       
        return (this.idUser == u.getIduser() &&
                this.name.equals(u.getName()) &&
                this.reputation==u.getReputation() &&
                this.bio.equals(u.getBio()) &&
                this.posts.equals(u.getPosts()));
    }
    
    /** Método toString */           
    public String toString (){
        StringBuilder sb = new StringBuilder();
        
        sb.append("Id do utilizador: ").append(this.idUser).append("\n")
           .append("Name: ").append(this.name).append("\n")
           .append("Reputation: ").append(this.reputation).append("\n")
           .append("Bio: ").append(this.bio).append("\n")
           .append("Posts: ").append(this.posts.toString()).append("\n");
        return sb.toString();
    }
    
    // Método que dado um intervalo de tempo, retorna os Ids das tags de posts realizados nesse intervalo
    public List<Long> getIdsTagsFiltered(LocalDate begin, LocalDate end){
        List<Long> idsTags = new ArrayList<>();
        List<Post> postsUserFiltered = this.posts.stream().
                      filter(p -> (p.getCreationDate().toLocalDate().equals(begin) ||
                                  p.getCreationDate().toLocalDate().equals(end) ||
                                 (p.getCreationDate().toLocalDate().isAfter(begin) &&
                                  p.getCreationDate().toLocalDate().isBefore(end))) &&
                                  p.isQuestion()).
                      collect(Collectors.toList());   
        postsUserFiltered.forEach((p) -> {
            idsTags.addAll(p.getIdTags());
        });
        
        return idsTags;
                
    }

    
    //Método que dado os posts de um outro utilizador, retorna a lista dos IDs das perguntas onde esses dois utilizadores participaram.
    //Pode um ter participiado via pergunta e outro via resposta, ou ambas via resposta.
    public List<Long> bothUsers(Set<Post> postsOtherUser){
        List<Long> res = new ArrayList<>();

        for(Post post : this.posts){
            if(post.isQuestion()){
                long id = post.getId();
                for(Post answer : postsOtherUser){
                    if(post.isAnswer())
                        if(answer.getParentId() == id && !res.contains(id))
                            res.add(id);

                }
            }
            else {
                long parentId = post.getParentId();
                for(Post answer : postsOtherUser){
                    if(post.isAnswer())
                        if(answer.getParentId() == parentId && !res.contains(parentId))
                            res.add(parentId);                 
                }
            }    
                
        }

        for(Post post : postsOtherUser){
            if(post.isQuestion()){
                long id = post.getId();
                for(Post answer : this.posts){
                    if(post.isAnswer())
                        if(answer.getParentId() == id && !res.contains(id))
                            res.add(id);
                }
            }
        }
        
        return res;

    }
    
}
     
   