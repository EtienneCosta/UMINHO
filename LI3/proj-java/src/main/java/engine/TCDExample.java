package engine;

import common.*;
import li3.*;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import javax.xml.stream.XMLStreamException;

public class TCDExample implements TADCommunity {

    private MyLog qelog;
    private StackOverflow so;

    //Método que inicializa as variáveis de instância
    public void init() {
        this.qelog = new MyLog("queryengine");
        this.so = new StackOverflow();
    }
    
    //Carregamento da estrutura
    public void load(String dumpPath){
       init();
        Parser parse = new Parser();
        try {
            Map<Long, User> users = parse.loadUsers(dumpPath);
            so.setUsers(users);
            Pair<PostsByDate, Map<Long, User>> postsAndUsers = parse.loadPosts(dumpPath, so.getUsers()); 
            so.setPosts(postsAndUsers.getFst());
            so.setUsers(postsAndUsers.getSnd());
                        
        } catch (FileNotFoundException | XMLStreamException ex) {
            ex.printStackTrace();
        }
        
        
    }
    
    //Método auxiliar que retorna a própria lista caso o N seja maior que o tamanho da lista, ou retorna uma sub-lista
    //caso o N seja menor que o tamanho da lista.
    private List<Long> resultList(Set<Post> posts, int N){
        if(posts.size() >=  N){
            return posts.stream().map(Post::getId).collect(Collectors.toList()).subList(0,N);
        }
        else return posts.stream().map(Post::getId).collect(Collectors.toList());
    }

    //Dado um id, retorna o titulo do post e o nome do autor.
    //Caso se trate de uma resposta, deve retornar titulo e nome do autor da pergunta. 
    public Pair<String,String> infoFromPost(long id) throws PostInexistenteException{
        Pair<String,String> res = new Pair<>(null,null);
        Post p = so.getPosts().getPost(id);
        
        if(p == null) throw new PostInexistenteException(Long.toString(id));

        if(p.isQuestion()){
            User u = so.getUsers().get(p.getUserId());
            res.setFst(p.getTitle());
            res.setSecond(u.getName());
        }
        
        else {
            Post p1 = so.getPosts().getPost(p.getParentId());
            User u = so.getUsers().get(p1.getUserId());
            res.setFst(p1.getTitle());
            res.setSecond(u.getName());

        }       
        return res;
    }
 
    //Coloca todos os Users num set ordenados pelo nr de posts a si associados. 
    //Retorna os N primeiros.
    public List<Long> topMostActive(int N) {
        Map<Long, User> users = so.getUsers();
        TreeSet<User> users1 = new TreeSet<>(new ComparatorNumberOfPost());
        users.values().forEach((u) -> {users1.add(u.clone());});

        return users1.stream().map(User::getIduser).collect(Collectors.toList()).subList(0, N);
    }

    //Filtra posts ocorridos durante o intervalo fornecido.
    //Para cada id retorna o tamanho das árvores de perguntas e respostas.
    public Pair<Long,Long> totalPosts(LocalDate begin, LocalDate end) {
        long numberQuestions = 0, numberAnswers= 0;
        Map<LocalDate, TreePair> postsFiltered = so.getPosts().postsFilteredDate(begin, end);
               
        for(TreePair pair : postsFiltered.values()){
            numberQuestions += pair.countQuestions();
            numberAnswers += pair.countAnswers();
        }
                
        return new Pair(numberQuestions, numberAnswers);
    }


    //Filtra posts ocorridos durante o intervalo fornecido.
    //Percorre o HashMap, para cada id vai buscar a árvore de perguntas, verificando, para cada uma se tem a tag.
    public List<Long> questionsWithTag(String tag, LocalDate begin, LocalDate end) {
        Set<Post> postsSorted = new TreeSet<>(new ComparatorPostDate());
        Map<LocalDate, TreePair> postsFiltered = so.getPosts().postsFilteredDate(begin, end);
     
        postsFiltered.values().stream().map((pair) -> pair.getQuestions()).
            forEachOrdered((questions) -> {questions.stream().filter((p) -> (p.containsTag(tag))).
                forEachOrdered((p) -> {postsSorted.add(p.clone());});});
        
        return postsSorted.stream().map(Post::getId).collect(Collectors.toList());
    }

    //Percorre o HashMap até encontrar o User com o id correspondente.
    //Devolve a sua bio e os seus últimos 10 posts.
    public Pair<String, List<Long>> getUserInfo(long id) throws UserInexistenteException{
        int N = 10;
        
        if(!so.getUsers().containsKey(id)){
            throw new UserInexistenteException(Long.toString(id));
        }
        
        User user = so.getUsers().get(id);
        String shortBio = user.getBio();
        List<Long> ids = resultList(user.getPosts(), N);
        
        return new Pair<>(shortBio,ids);
    }

    //Filtra posts ocorridos no intervalo fornecido.
    //Percorre o HashMap com os posts filtrados, para cada id vai buscar a árvore de respostas a si associadas.
    //Coloca num Set os posts ordenados pelo Score.
    public List<Long> mostVotedAnswers(int N, LocalDate begin, LocalDate end) {
        Set<Post> postsByScore = new TreeSet<>(new ComparatorPostScore());
        Map<LocalDate, TreePair> postsFiltered = so.getPosts().postsFilteredDate(begin, end);
        
        postsFiltered.values().stream().map((pair) -> pair.getAnswers()).
            forEachOrdered((answers) -> {answers.stream().
                forEachOrdered((p) -> {postsByScore.add(p.clone());});});  
        
        return resultList(postsByScore, N);
    }

    //Filtra posts ocorridos no intervalo fornecido.
    //Percorre o HashMap com os posts filtrados, para cada id vai buscar a árvore de perguntas.
    //Coloca num Set os posts ordenados pelo número de respostas a si associadas.
    //Adoptamos a versão em que o número de respostas é o total
    public List<Long> mostAnsweredQuestions(int N, LocalDate begin, LocalDate end) {
        Set<Post> postsByAnswers = new TreeSet<>(new ComparatorPostMoreAnswers());
        Map<LocalDate, TreePair> postsFiltered = so.getPosts().postsFilteredDate(begin, end);
        
        postsFiltered.values().stream().map((pair) -> pair.getQuestions()).
            forEachOrdered((questions) -> {questions.stream().
                forEachOrdered((p) -> {postsByAnswers.add(p.clone());});});   
        
        return resultList(postsByAnswers, N);
    }
    
    
    //Filtra posts ocorridos no intervalo fornecido.
    /*Percorre o HashMap com os posts filtrados, para cada id vai buscar a árvore de perguntas,verificando 
    para cada uma se nela existe a palavra fornecida.*/
    //Coloca num Set os posts ordenados pela sua data de criação.
    public List<Long> containsWord(int N, String word) {
        Set<Post> postsSorted = new TreeSet<>(new ComparatorPostDate());
        Map<LocalDate, TreePair> posts = so.getPosts().getPostsByDate();
        
        posts.values().stream().map((pair) -> pair.getQuestions()).
            forEachOrdered((questions) -> {questions.stream().filter((p) -> (p.containsWord(word))).
                forEachOrdered((p) -> {postsSorted.add(p.clone());});});     
        
        return resultList(postsSorted, N);
    }

    //Percorre os posts dos 2 users.
    /*Para cada um, se este for questão percorre os Posts do User 2, verificando, para cada
    um, se este é resposta ao post do user 1;caso este seja uma resposta,percorre os posts do user 2 
    e para cada post que for resposta, faz a verificação se ambos os posts são resposta ao mesmo post pergunta
    (pelo parentId );*/
    public List<Long> bothParticipated(int N, long id1, long id2) throws UserInexistenteException{
        Map<Long, User> users = so.getUsers();
        
        if(!users.containsKey(id1) || !users.containsKey(id2)){
            throw new UserInexistenteException(Long.toString(id1));
        }
        List<Long> idsQuestions = users.get(id1).bothUsers(users.get(id2).getPosts());
        if(idsQuestions.size() >=  N){
            return idsQuestions.subList(0,N);
        }
        else return idsQuestions;
    }

    /* Percorre o HashMap PostsByDate, e para cada par de arvóres vai procurar as respostas à
    pergunta fornecida como input. Para cada resposta, calcula a média.*/
    public long betterAnswer(long id) throws PostInexistenteException{
        Set<Post> answers = new HashSet<>();
        Map<Long,User> usersOfAnswers = new HashMap<>();
        boolean questionExists = false;
        
        for(TreePair tp : so.getPosts().getPostsByDate().values()){
            if(! questionExists && tp.existQuestion(id)) questionExists = true;
            Set<Post> answersOfQuestion = tp.getAnswersOfQuestion(id);
            answers.addAll(answersOfQuestion);           
            for(Post p : answersOfQuestion){
                usersOfAnswers.put(p.getUserId(), so.getUsers().get(p.getUserId()));
            }        
        }
        
        if(!questionExists) throw new PostInexistenteException(Long.toString(id));
        
        long betterAnswerId = -1;
        double betterClassification = -1;
        for(Post p : answers){
            User user = usersOfAnswers.get(p.getUserId());
            double classification = p.answerClassification((double)user.getReputation());
            if(classification > betterClassification){
                betterClassification = classification;
                betterAnswerId = p.getId();
            }            
        }        
        return betterAnswerId;
    }

    //Filtra posts que são perguntas ocorridos durante o intervalo de tempo fornecido.
    /*Encontra users que efetuaram posts durante esse intervalo, coloca os num Set 
    ordenado pela reputação e retorna, para cada um, a tag mais usada pelo mesmo.*/
    public List<Long> mostUsedBestRep(int N, LocalDate begin, LocalDate end) {
        List<Long> res = new ArrayList<>();
        Map<Long, User> users = so.getUsers();
        Map<Long, Integer> occurrences = new HashMap<>();
        Set<User> users1 = new TreeSet<>(new ComparatorUserReputation());
        Set<Tag> tags = new TreeSet<>(new ComparatorTagOcorrencias());
        
        users.values().stream().forEachOrdered((u) -> {
            users1.add(u.clone());});
        
        List<User> nUsersBestReputation = users1.stream().map(User::clone).collect(Collectors.toList()).subList(0,N);
             
        nUsersBestReputation.forEach((u) -> {
            res.addAll(u.getIdsTagsFiltered(begin, end));
        });
        
        res.forEach((id) -> {
            int ocorrencias = Collections.frequency(res, id);
            occurrences.put(id, ocorrencias);
        });
        
        occurrences.entrySet().stream().map((ocorrencia) -> new Tag(ocorrencia.getKey(), ocorrencia.getValue())).forEachOrdered((t) -> {
            tags.add(t.clone());
        });
        
        if(tags.size() >=  N){
            return tags.stream().map(Tag::getIdTag).collect(Collectors.toList()).subList(0,N);
        }
        else return tags.stream().map(Tag::getIdTag).collect(Collectors.toList());
    }

    public void clear(){
        so = null;
        System.gc();
    }
}
