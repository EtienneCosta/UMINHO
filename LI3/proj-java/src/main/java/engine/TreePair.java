package engine;
import common.ComparatorPostDate;
import java.util.Set;
import java.util.TreeSet;


public class TreePair {
    private Set<Post> questions; //Set de posts que são questões
    private Set<Post> answers;   //Set de posts que são respostas
    
    /****************
    * Constructores *
    ****************/
    
    //Vazio
    public TreePair(){
        ComparatorPostDate comp = new ComparatorPostDate();
        this.questions = new TreeSet<>(comp);
        this.answers = new TreeSet<>(comp);
    }
    
    //Parametrizado
    public TreePair(Set<Post> questions, Set<Post> answers){
        setQuestions(questions);
        setAnswers(answers);
    }
    
    //De cópia
    public TreePair(TreePair pair){
        this.questions = pair.getQuestions();
        this.answers = pair.getAnswers();
    }
    
    /****************
    *   Getters    *
    ****************/
    public Set<Post> getQuestions(){
        Set<Post> questions = new TreeSet<>(new ComparatorPostDate());
        for(Post p : this.questions)
            questions.add(p.clone());
        return questions;
    }
    
    public Set<Post> getAnswers(){
        Set<Post> answers = new TreeSet<>(new ComparatorPostDate());
        for(Post p : this.answers)
            answers.add(p.clone());
        return answers;
    }
    
    /****************
    *    Setters    *
    ****************/
    public void setQuestions(Set<Post> questions){
        this.questions = new TreeSet<>(new ComparatorPostDate());
        questions.forEach((p) -> this.questions.add(p.clone()));
    }
    
    public void setAnswers(Set<Post> answers){
        this.answers = new TreeSet<>(new ComparatorPostDate());
        answers.forEach((p) -> this.answers.add(p.clone()));
    }
    
    //Método clone
    public TreePair clone(){
        return new TreePair(this);
    }
    
    //Método equals
    public boolean equals(Object o){
    if (this == o) return true;
    if ((o == null) || (this.getClass() != o.getClass())) return false;
    TreePair pair = (TreePair) o;
        return this.questions.equals(pair.getQuestions()) &&
               this.answers.equals(pair.getAnswers());
    }
    
    //Método toString
    public String toString() {
    StringBuffer sb = new StringBuffer();
    for(Post p : this.questions)
        sb.append(p.toString() + "\n");
    for(Post p : this.answers)
        sb.append(p.toString() + "\n");
    return sb.toString(); 
    }
    
    //Método que adiciona um post ao Set de questões
    public void addQuestion(Post p){
       this.questions.add(p);
    }
    
    //Método que adiciona um post ao Set de respostas
    public void addAnswer(Post p){
       this.answers.add(p);
    }

    /** Método que devolve um Post dado o seu Id */
    public Post getPost(long id){
        Post res = new Post ();
      
        for(Post q : this.questions){
            if(q.getId()==id){
                res=q.clone();
                return res;
            }
        }
    
        for(Post a:this.answers){
            if(a.getId()==id){
                res=a.clone();
                return res;
            }
        }
        return null;
    }
    
    //Método que retorna o tamanho do Set de questões
    public long countAnswers(){
        return this.answers.size();
    }
    
    //Método que retorna o tamanho do Set de respostas
    public long countQuestions(){
        return this.questions.size();
    }
    
    //Método que dado o ID de uma pergunta, retorna as respostas a essa pergunta
    public Set<Post> getAnswersOfQuestion(long id){
        Set<Post> res = new TreeSet<>(new ComparatorPostDate());

        for(Post p : this.answers){
            if(p.getParentId() == id) res.add(p);
        }

        return res;
    }
    
    //Método que verifica se o post existe no set de questões ou no de respostas
    public boolean existPost(long id){
        
        if (this.questions.stream().anyMatch((q) -> (q.getId()==id))) {
            return true;
        }
    
        else if (this.answers.stream().anyMatch((a) -> (a.getId()==id))) {
            return true;
        }
        
        return false;
    }
    
    public boolean existQuestion(long id){
        
        if (this.questions.stream().anyMatch((q) -> (q.getId()==id))) {
            return true;
        }
        
        return false;
    }
}
