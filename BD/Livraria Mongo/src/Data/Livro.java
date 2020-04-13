package Data;

public class Livro {
    private int isbn;
    private String titulo;
    private String editora;
    private int edicao;
    private int ano;
    private int stock;
    private int paginas;
    private double preco;
    private String idioma;
    private String categoria;
    private String escritor;

    public Livro(int isbn, String titulo, String editora, int edicao, int ano, int stock, int paginas, double preco, String idioma, String categoria, String escritor){
        this.isbn = isbn;
        this.titulo = titulo;
        this.editora = editora;
        this.edicao = edicao;
        this.ano = ano;
        this.stock = stock;
        this.paginas = paginas;
        this.preco = preco;
        this.idioma = idioma;
        this.categoria= categoria;
        this.escritor = escritor;
    }

    public String getTitulo(){
        return this.titulo;
    }

    public double getPreco() {
        return preco;
    }

    public int getAno() {
        return ano;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public int getEdicao() {
        return edicao;
    }

    public void setEdicao(int edicao) {
        this.edicao = edicao;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getPaginas() {
        return paginas;
    }

    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEscritor() {
        return escritor;
    }

    public void setEscritor(String escritor) {
        this.escritor = escritor;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("ISBN: " + isbn + "\n");
        sb.append("Editora: " + editora + "\n");
        sb.append("Título: " + titulo + "\n");
        sb.append("Edição: " + edicao + "\n");
        sb.append("Ano: " + ano + "\n");
        sb.append("Stock: " + stock + "\n");
        sb.append("Páginas: " + paginas + "\n");
        sb.append("Preço: " + preco + "\n");
        sb.append("Idioma: " + idioma + "\n");
        sb.append("Categoria: " + categoria + "\n");
        sb.append("Escritor: " + escritor + "\n");
        return sb.toString();
    }
}
