package engine;


import common.ComparatorPostDate;
import java.util.Iterator;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.*;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Set;
import java.util.TreeSet;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import common.Pair;

public class Parser {
    
    private Map <String, Long> tags; // Estrutura onde se guardam as tags. Key - Nome da tag, Value - ID da tag
    
    //Construtor por omissão
    public Parser(){
        this.tags = new HashMap<>();
    }
    
    //Carregamento dos utilizadores
    public Map<Long, User> loadUsers(String dump_path) throws FileNotFoundException, XMLStreamException{
        Map<Long, User> users = new HashMap<>();

        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        InputStream in = new FileInputStream(dump_path + "Users.xml");
        XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
    

    
        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
        
            if (event.isStartElement()) {
        
                StartElement startElement = event.asStartElement();
        
                if (startElement.getName().getLocalPart().equals("row")) {
                long id = 0;                                
                String name = "";
                long reputation = 0;
                String bio = "";
                Iterator<Attribute> attributes = startElement.getAttributes();
                int i = 0;

                    while (attributes.hasNext() /*&& i < 4*/) {
                        Attribute attribute = attributes.next();
                        if (attribute.getName().toString().equals("Id")) {
                            id = Long.parseLong(attribute.getValue());
                            i++;
                        }
                        else if(attribute.getName().toString().equals("DisplayName")){
                            name = attribute.getValue();
                            i++;
                        }
                        else if(attribute.getName().toString().equals("Reputation")){
                            reputation =  Long.parseLong(attribute.getValue());
                            i++;
                        }
                        else if(attribute.getName().toString().equals("AboutMe")){
                            bio = attribute.getValue();
                            i++;
                        }
                    }
                        if(id>0){
                            Set<Post> posts = new TreeSet<>(new ComparatorPostDate());
                            User user = new User(id, name, reputation, bio, posts);
                            users.put(user.getIduser(), user.clone());
                        }
                }
        
                
            }
        
            

        }
        
        return users;
    }
   
    //Carregamento dos posts
    public Pair<PostsByDate, Map<Long, User>>  loadPosts(String dump_path, Map<Long, User> users) throws FileNotFoundException, XMLStreamException{
        loadTags(dump_path);
        PostsByDate postsByDate = new PostsByDate();
        
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        InputStream in = new FileInputStream(dump_path + "Posts.xml");
        XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
       
        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
        
            if (event.isStartElement()) {
        
                StartElement startElement = event.asStartElement();
        
                if (startElement.getName().getLocalPart().equals("row")) {
                    long id = 0;
                    long postTypeId = 0;
                    String creationDate = "";
                    long userId = 0;
                    String title = "";
                    String tags = "";
                    long answerCount = 0;
                    long commentCount = 0;           
                    long score = 0;
                    String parentId = "";
                    Iterator<Attribute> attributes = startElement.getAttributes();
                    int i = 0;
    
                    while (attributes.hasNext() /*&& i < 10*/) {
                        Attribute attribute = attributes.next();
                        if (attribute.getName().toString().equals("Id")) {
                            id = Long.parseLong(attribute.getValue());
                            i++;
                        }
                        else if(attribute.getName().toString().equals("PostTypeId")){
                            postTypeId = Long.parseLong(attribute.getValue());
                            i++;
                        }
                        else if(attribute.getName().toString().equals("CreationDate")){
                            creationDate = attribute.getValue();
                            i++;
                        }
                        else if(attribute.getName().toString().equals("OwnerUserId")){
                            userId =  Long.parseLong(attribute.getValue());
                            i++;
                        }
                        else if(attribute.getName().toString().equals("Title")){
                            title = attribute.getValue();
                            i++;
                        }
                        else if(attribute.getName().toString().equals("Tags")){
                            tags = attribute.getValue();
                            i++;
                        }
                        else if(attribute.getName().toString().equals("AnswerCount")){
                            answerCount = Long.parseLong(attribute.getValue());
                            i++;
                        }
                        else if(attribute.getName().toString().equals("CommentCount")){
                            commentCount = Long.parseLong(attribute.getValue());
                            i++;
                        }
                        else if(attribute.getName().toString().equals("Score")){
                            score = Long.parseLong(attribute.getValue());
                            i++;
                        }
                        else if(attribute.getName().toString().equals("ParentId")){
                            parentId = attribute.getValue();
                            i++;
                        }
                    }

                    long parentI = 0;                
                    if(!parentId.equals(""))
                       parentI = Long.parseLong(parentId);
    
                    LocalDateTime date = LocalDateTime.parse(creationDate);                                      
                    List<String> tags1 = new ArrayList<>();
                    List<Long> idTags = new ArrayList<>();              

                    String[] st = tags.split("<|>");               
                    for(String s : st){
                        if(!s.equals(""))
                            tags1.add(s);
                    }
                        
                    for(String s : tags1){
                        if(this.tags.containsKey(s)){
                            long idTag = this.tags.get(s);
                            idTags.add(idTag);
                        }
                    }
                    
                    if(userId > 0){
                        Post post = new Post(id, postTypeId, date, userId, title, answerCount, commentCount, score, tags1, idTags, parentI);
                        postsByDate.addPost(post);


                        User user = users.get(userId);
                        Set<Post> posts = user.getPosts();
                        posts.add(post);
                        user.setPosts(posts);
                    }
                }                   
                            
            }            
        }
        
        return new Pair(postsByDate, users);
    }
   
    //Carregamento das tags
    public void loadTags(String dump_path) throws FileNotFoundException, XMLStreamException{
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        InputStream in = new FileInputStream(dump_path + "Tags.xml");
        XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
    
        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
        
            if (event.isStartElement()) {
        
                StartElement startElement = event.asStartElement();
        
                if (startElement.getName().getLocalPart().equals("row")) {
                long id = 0;                                
                String name = "";
                int i  = 0;

                Iterator<Attribute> attributes = startElement.getAttributes();

                    while (attributes.hasNext() /* && i < 2*/) {
                        Attribute attribute = attributes.next();
                        if (attribute.getName().toString().equals("Id")) {
                            id = Long.parseLong(attribute.getValue());
                            i++;
                        }
                        else if(attribute.getName().toString().equals("TagName")){
                            name = attribute.getValue();
                            i++;
                        }
                    }
                
                this.tags.put(name, id);
                }
        
                
            }          

        }
    }

}
      