package Data;

import java.time.LocalDate;

public class Escritor {
    private int id;
    private String nome;
    private String nacionalidade;
    private LocalDate data_nascimento;
    private String biografia;

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Id: " + id + "\n");
        sb.append("Nome: " + nome + "\n");
        sb.append("Nacionalidade: " + nacionalidade + "\n");
        sb.append("Data: " + data_nascimento + "\n");
        sb.append("Biografia: " + biografia + "\n");
        return sb.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public LocalDate getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(LocalDate data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getNome(){
        return this.nome;
    }
    public Escritor(int id, String nome, String nacionalidade, LocalDate d, String biografia){
        this.id = id;
        this.nome = nome;
        this.nacionalidade = nacionalidade;
        this.data_nascimento = d;
        this.biografia = biografia;
    }


}
