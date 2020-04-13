package Data;

import DAO.DatabaseDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dados {
    private DatabaseDAO database;
    private Map<Integer, Livro> livros;
    private Map<Integer, Escritor> escritores;
    private Map<Integer, Funcionario> funcionarios;
    private Map<Integer, Cliente> clientes;
    private Map<Integer, String> categorias;
    private Map<Integer, Venda> vendas;


    public Dados(){
        this.database = new DatabaseDAO();
        this.livros = new HashMap<>();
        this.escritores = new HashMap<>();
        this.clientes = new HashMap<>();
        this.categorias = new HashMap<>();
        this.vendas = new HashMap<>();

    }

    public void init(){
        try {
            database.loadEscritores();
            database.loadFuncionarios();
            database.loadClientes();
            database.loadCategorias();
            database.loadLivros();
            database.loadVendas();
        } catch (Exception e) {
            System.out.println("Deu erro");
        }
    }
    public void getData(){
            this.escritores = database.getEscritores();
            this.funcionarios = database.getFuncionarios();
            this.clientes = database.getClientes();
            this.categorias = database.getCategorias();
            this.livros = database.getLivros();
            this.vendas = database.getVendas();
    }

    public void export2mongo(){
        database.setClientes();
        database.setEscritores();
        database.setLivros();
        database.setFuncionarios();
        database.setVendas();
    }


    public void printEscritores(){
        for(Escritor e: this.escritores.values())
            System.out.println(e.toString());
    }
}
