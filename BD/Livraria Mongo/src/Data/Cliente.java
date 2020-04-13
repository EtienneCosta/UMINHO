package Data;

import java.time.LocalDate;

public class Cliente {
    private int id;
    private String nome;
    private LocalDate data_nascimento;
    private String localidade;
    private String rua;
    private String cp;
    private String telefone;
    private String email;

    public Cliente(int id, String nome, LocalDate d, String l, String rua, String cp, String tel, String email){
        this.id = id;
        this.nome = nome;
        this.data_nascimento = d;
        this.localidade = l;
        this.rua = rua;
        this.cp = cp;
        this.telefone = tel;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(LocalDate data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Id: " + id + "\n");
        sb.append("Nome: " + nome + "\n");
        sb.append("Data: " + data_nascimento + "\n");
        sb.append("Rua: " + rua + "\n");
        sb.append("CP: " + cp + "\n");
        sb.append("Telefone: " + telefone + "\n");
        sb.append("E-mail: " + email + "\n");
        return sb.toString();
    }
}
