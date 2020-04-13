/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

/**
 *
 * @author joanacruz
 */
public class Tag {
    private long idTag;
    private int ocorrencia;
    
    public Tag(long idTag, int ocorrencia){
        this.idTag = idTag;
        this.ocorrencia = ocorrencia;
    }
    
    public long getIdTag(){
        return this.idTag;
    }
    
    public int getOcorrencia(){
        return this.ocorrencia;
    }
    
    public void setIdTag(long idTag){
        this.idTag = idTag;
    }
    
    public void setOcorrencia(int ocorrencia){
        this.ocorrencia = ocorrencia;
    }
    
    public boolean equals(Object o){
        if(o==this) return true;
        else if(o==null||o.getClass()!=this.getClass()) return false;
        Tag t=(Tag) o;       
        return (this.idTag == t.getIdTag() &&
                this.ocorrencia == t.getOcorrencia());
    }
    
    public Tag clone(){
        return new Tag(idTag, ocorrencia);
    }
}
