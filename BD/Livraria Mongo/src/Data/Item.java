package Data;

public class Item {
    private int isbn;
    private String titulo;
    private int quantidade;
    private double preco;

    public Item(int isbn, String titulo, int quantidade, double preco){
        this.isbn = isbn;
        this.titulo = titulo;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Id: " + isbn + "\n");
        sb.append("Titulo: " + titulo + "\n");
        sb.append("Quantidade: " + quantidade + "\n");
        sb.append("Preço: " + preco + "\n");
        return sb.toString();
    }
}
