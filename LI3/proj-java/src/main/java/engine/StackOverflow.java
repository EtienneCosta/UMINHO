package engine;

import java.util.Map;
import java.util.HashMap;


public class StackOverflow {
    // variáveis de instância 
    private Map <Long,User> users;
    private PostsByDate postsByDate;
    
    //Construtor por omissão
    public StackOverflow(){
         this.users = new HashMap<>();
         this.postsByDate = new PostsByDate();
    }
    
    /****************
    *   Getters    *
    ****************/
    public Map<Long, User> getUsers(){
        return this.users;/*.entrySet()
                           .stream()
                           .collect(Collectors.toMap(e->e.getKey(), e->e.getValue().clone()));  
 */
    }
    
    public PostsByDate getPosts() {
        return this.postsByDate; /*.clone();*/
    }
    
    /****************
    *    Setters    *
    ****************/
    public void setUsers(Map<Long, User> users) {
         this.users = users;
         /*.entrySet()
                             .stream()
                             .collect(Collectors.toMap(e->e.getKey(), e->e.getValue().clone())); 
 */
    }

    public void setPosts(PostsByDate posts) {
         this.postsByDate = posts; /*.clone();*/
    }
    
    //Método que retorna o utilizador correspondente ao ID fornecido
    public User getUser(Long id) throws UserInexistenteException {
         User u;
         if(!this.users.containsKey(id)) {
             throw new UserInexistenteException();
         }
         u = this.users.get(id).clone();

         return u;
     }
}