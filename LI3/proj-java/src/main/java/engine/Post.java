package engine;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Joana Marta Cruz
 */
public class Post{
    private long id; //ID do post
    private long postTypeId; //Tipo do post. 1 caso seja pergunta, 2 caso seja resposta
    private LocalDateTime creationDate; //Data de criação do post
    private long userId; //ID do user que efetuou o post
    private String title; //Título do post
    private long answerCount; //Número de respostas do post
    private long commentCount; //Número de comentários do post
    private long score; //Score do post
    private List<String> tags; //Lista de tags do post
    private List<Long> idTags; //Lista dos IDS das tags do post
    private long parentId; //Só existe caso o post seja uma resposta. ID da pergunta à qual a resposta corresponde.
    
    /****************
     * Construtores *
     ****************/
    
    //Construtor por omissão
    public Post(){
        this.id = 0;
        this.postTypeId = 0;
        this.creationDate = LocalDateTime.now();
        this.userId = 0;
        this.title = "";
        this.answerCount = 0;
        this.commentCount = 0;
        this.score = 0;
        this.idTags = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.parentId = 0;
        
    }
    
    //Construtor parametrizado
    public Post(long id, long postTypeId, LocalDateTime creationDate, long userId, String title, long answerCount, long commentCount, long score, List<String> tags, List<Long> idTags, long parentId){
        this.id = id;
        this.postTypeId = postTypeId;
        this.creationDate = creationDate;
        this.userId = userId;
        this.title = title;
        this.answerCount = answerCount;
        this.commentCount = commentCount;
        this.score = score;
        setTags(tags);
        setIdTags(idTags);
        this.parentId = parentId;
    }
    
    //Construtor de cópia
    public Post(Post p){
        this.id = p.getId();
        this.postTypeId = p.getPostTypeId();
        this.creationDate = p.getCreationDate();
        this.userId = p.getUserId();
        this.title = p.getTitle();
        this.answerCount = p.getAnswerCount();
        this.commentCount = p.getCommentCount();
        this.score = p.getScore();
        this.tags = p.getTags();
        this.idTags = p.getIdTags();
        this.parentId = p.getParentId();
    }
    
    /****************
     *   Getters    *
     ****************/
    public long getId(){
        return this.id;
    }
     
    public long getPostTypeId(){
        return this.postTypeId;
    }
    
    public LocalDateTime getCreationDate(){
        return this.creationDate;
    }
    
    public long getUserId(){
        return this.userId;
    }
    
    public String getTitle(){
        return this.title;
    }
    
    public long getAnswerCount(){
        return this.answerCount;
    }
    
    public long getCommentCount(){
        return this.commentCount;
    }
    
    public long getScore(){
        return this.score;
    }
    
    public List<String> getTags(){
        List<String> res = new ArrayList<>();
        for(String s : this.tags){
            res.add(s);
        }
        return res;
    }
    
    public List<Long> getIdTags(){
        List<Long> res = new ArrayList<>();
        for(Long i : this.idTags){
            res.add(i);
        }
        return res;
    }
    
    public long getParentId(){
        return this.parentId;
    }
    
    /****************
    *    Setters    *
    ****************/
    public void setId(long id){
        this.id = id;
    }
    
    public void setPostTypeId(long postTypeId){
        this.postTypeId = postTypeId;
    }
    
    public void setCreationDate(LocalDateTime creationDate){
        this.creationDate = creationDate;
    }
    
    public void setUserId(long userId){
        this.userId = userId;
    }
    
    public void setTitle(String title){
        this.title = title;
    }
    
    public void setAnswerCount(long answerCount){
        this.answerCount = answerCount;
    }
    
    public void setCommentCount(long commentCount){
        this.commentCount = commentCount;
    }
    
    public void setScore(long score){
        this.score = score;
    }

    public void setTags(List<String> tags){
        this.tags = new ArrayList<>();
        tags.forEach(s-> {this.tags.add(s);});
    }
    
    public void setIdTags(List<Long> idTags){
        this.idTags = new ArrayList<>();
        idTags.forEach(s-> {this.idTags.add(s);});
    }
    
    public void setParentId(long parentId){
        this.parentId = parentId;
    }
    
    //Clone - retorna um clone do post
    public Post clone(){
        return new Post(this);
    }
    
    //Equals - testa se dois posts são iguais
    public boolean equals(Object o){
    if(o == this) return true;
    if(o == null || o.getClass() != this.getClass()) return false;
    Post p = (Post) o;
    return this.id == p.getId() &&
           this.postTypeId == p.getPostTypeId() &&
           this.creationDate.equals(p.getCreationDate()) &&
           this.userId == p.getUserId() &&
           this.title.equals(p.getTitle()) &&
           this.answerCount == p.getAnswerCount() &&
           this.commentCount == p.getCommentCount() &&
           this.score == p.getScore() &&
           this.tags.equals(p.getTags()) &&
           this.idTags.equals(p.getIdTags()) &&
           this.parentId == p.getParentId();
    }
    
    //Método toString
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(id).append("\nPostTypeId: ").append(postTypeId).append("\nDate: ").append(creationDate).
        append("\nId do User: ").append(userId).append("\nTitle: ").append(title).append("\nNumber of answers: ").append(answerCount).
        append("\nNumber of comments: ").append(commentCount).append("\nScore: ").append(score).
        append("\nTags: ").append(tags.toString()).append("\nId das Tags: ").append(idTags.toString()).
        append("\nParent Id: ").append(parentId);
        return sb.toString();
                          
    }
    
    //Testa se um post é uma questão
    public boolean isQuestion(){
        return this.postTypeId == 1;
    }
    
    //Testa se um post é uma resposta
    public boolean isAnswer(){
        return this.postTypeId == 2;
    }
    
    //Testa se um post contêm uma determinada tag
    public boolean containsTag(String tag){
        for(String s : this.tags){
            if(s.equals(tag)) return true;
        }
        return false;
    }
    
    //Testa se o título de um post contêm uma determinada palavra
    public boolean containsWord(String word){
        return this.title.contains(word);
    }
    
    //Calcula a classificação de um post
    public double answerClassification(double userReputation){
        return ((double)0.65 * this.score + 
                (double)0.25 * userReputation + 
                (double)0.1 * this.commentCount);
    }
}
