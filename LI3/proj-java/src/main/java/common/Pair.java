package common;

import java.util.Objects;

public class Pair<X,Y> {
    private X fst;
    private Y snd;
    
    //Construtor parametrizado
    public Pair(X fst, Y snd) {
        this.fst = fst;
        this.snd = snd;
    }

    public int hashCode() {
        int hashFst = fst != null ? fst.hashCode() : 0;
        int hashSnd = snd != null ? snd.hashCode() : 0;

        return (hashFst + hashSnd) * hashSnd + hashFst;
    }
    
    //Método Equals que testa se ambos os valores do par sao iguais ao objeto recebido
    public boolean equals(Object o) {
        if (o instanceof Pair) {
            Pair p = (Pair) o;
            return Objects.equals(this.fst, p.fst) && Objects.equals(snd, p.snd);
        }
        return false;
    }
    //Método toString
    public String toString() {
        return "(" + fst + ", " + snd + ")";
    }
    //Getter
    public X getFst() {
        return fst;
    }
    //Setter
    public void setFst(X fst) {
        this.fst = fst;
    }
    //Getter
    public Y getSnd() {
        return snd;
    }
    //Setter 
    public void setSecond(Y snd) {
        this.snd = snd;
    }
    
    public void setSnd(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}