package DAO;

import Data.*;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sun.jna.platform.win32.OaIdl;
import org.bson.Document;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class DatabaseDAO {
    private Connection connection;
    private Connect con;

    public static MongoClient mongoClient;
    public static DB db;

    private Map<Integer, Livro> livros;
    private Map<Integer, Escritor> escritores;
    private Map<Integer, Funcionario> funcionarios;
    private Map<Integer, Cliente> clientes;
    private Map<Integer, String> categorias;
    private Map<Integer, Venda> vendas;

    public void loadEscritores() throws Exception {
        Map<Integer, Escritor> res = new HashMap<>();
        String nome, nacionalidade, biografia;
        int id;
        LocalDate data;
        connection = con.connect();
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM escritor");
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            id = rs.getInt("idEscritor");
            nome = rs.getString("Nome");
            nacionalidade = rs.getString("Nacionalidade");
            data = rs.getDate("DataNascimento").toLocalDate();;
            biografia = rs.getString("Biografia");

            Escritor e = new Escritor(id, nome, nacionalidade, data, biografia);
            System.out.println(e.toString());
            res.put(id,e);
        }
        con.close(connection);
        this.escritores = res;
    }

    public void loadFuncionarios() throws Exception {
        Map<Integer, Funcionario> res = new HashMap<>();
        String nome, localidade, rua, cp, tel, email;
        double vencimento;
        int id;
        LocalDate data;
        connection = con.connect();
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM funcionario");
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            id = rs.getInt("idFuncionario");
            nome = rs.getString("Nome");
            data = rs.getDate("DataNascimento").toLocalDate();;
            localidade = rs.getString("Localidade");
            rua = rs.getString("Rua");
            cp = rs.getString("CodigoPostal");
            tel = rs.getString("Telefone");
            vencimento = rs.getDouble("Vencimento");
            email = rs.getString("Email");

            Funcionario f = new Funcionario(id, nome, data, localidade, rua, cp, tel, vencimento, email);
            System.out.println(f.toString());
            res.put(id,f);
        }
        con.close(connection);
        this.funcionarios = res;
    }

    public void loadClientes() throws Exception {
        Map<Integer, Cliente> res = new HashMap<>();
        String nome, localidade, rua, cp, tel, email;
        int id;
        LocalDate data;
        connection = con.connect();
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM cliente");
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            id = rs.getInt("idCliente");
            nome = rs.getString("Nome");
            data = rs.getDate("DataNascimento").toLocalDate();;
            localidade = rs.getString("Localidade");
            rua = rs.getString("Rua");
            cp = rs.getString("CodigoPostal");
            tel = rs.getString("Telefone");
            email = rs.getString("Email");

            Cliente c = new Cliente(id, nome, data, localidade, rua, cp, tel, email);
            System.out.println(c.toString());
            res.put(id,c);
        }
        con.close(connection);
        this.clientes = res;
    }

    public void loadCategorias() throws Exception {
        Map<Integer, String> res = new HashMap<>();
        String categoria;
        int id;
        connection = con.connect();
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM categoria");
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            id = rs.getInt("idCategoria");
            categoria = rs.getString("Genero");
            System.out.println(id + " " + categoria);
            res.put(id,categoria);
        }
        con.close(connection);
        this.categorias = res;
    }

    public void loadLivros() throws Exception {
        Map<Integer, Livro> res = new HashMap<>();
        String titulo, editora, idioma;
        int isbn, edicao, ano, stock, paginas, categoria,escritor;
        String autor;
        double preco;
        connection = con.connect();
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM Livro");
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            isbn = rs.getInt("Isbn");
            titulo = rs.getString("Titulo");
            editora = rs.getString("Editora");
            edicao = rs.getInt("Edicao");
            ano = rs.getInt("Ano");
            stock = rs.getInt("Stock");
            paginas = rs.getInt("Paginas");
            preco = rs.getDouble("Preco");
            idioma = rs.getString("Idioma");
            categoria = rs.getInt("Categoria");
            escritor = rs.getInt("Escritor");

            autor = escritores.get(escritor).getNome();
            Livro l = new Livro(isbn, titulo, editora, edicao, ano, stock, paginas, preco, idioma, categorias.get(categoria), autor);
            System.out.println(l.toString());
            res.put(isbn,l);
        }
        con.close(connection);

        this.livros = res;
    }

    public List<Item> loadItems(String key) throws Exception {
        List<Item> res = new ArrayList<>();
        String categoria;
        int id_livro, id_venda, quantidade;
        double preco;
        connection = con.connect();
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM LivroVenda where Venda = ?");
        stm.setString(1, key);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            id_livro = rs.getInt("Livro");
            quantidade = rs.getInt("Quantidade");
            preco = rs.getDouble("Preco");
            Item i = new Item(id_livro, livros.get(id_livro).getTitulo(),quantidade, preco);
            res.add(i);
        }
        con.close(connection);
        return res;
    }

    public void loadVendas() throws Exception {
        Map<Integer, Venda> res = new HashMap<>();
        String categoria;
        int id, quantidade, id_func, id_cliente;
        LocalDate data;
        double cupao, total;

        connection = con.connect();
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM Venda");
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            id = rs.getInt("idVenda");
            data = rs.getDate("Data").toLocalDate();
            cupao = rs.getDouble("Cupao");
            total = rs.getDouble("Total");
            id_func = rs.getInt("Funcionario");
            id_cliente = rs.getInt("Cliente");
            Venda v = new Venda(id, data, cupao, total, id_func, id_cliente, loadItems(Integer.toString(id)));
            System.out.println(v.toString());
            res.put(id, v);
        }
        con.close(connection);
        this.vendas = res;
    }

    public void setLivros() {
        MongoConnect  c =  new MongoConnect();
        MongoDatabase db = c.getDb();

        Document doc;
        MongoCollection<Document> livrosColl = db.getCollection("livros");
        for (Livro l : livros.values()) {
            doc = new Document();
            doc.put("ISBN:", l.getIsbn());
            doc.put("titulo", l.getTitulo());
            doc.put("editora", l.getEditora());
            doc.put("ano", l.getAno());
            doc.put("stock", l.getStock());
            doc.put("paginas", l.getPaginas());
            doc.put("preco", l.getPreco());
            doc.put("idioma", l.getIdioma());
            doc.put("Categoria", l.getCategoria());
            doc.put("Escritor", l.getEscritor());
            livrosColl.insertOne(doc);
        }
        c.close();
    }

    public void setFuncionarios() {
        MongoConnect  c =  new MongoConnect();
        MongoDatabase db = c.getDb();

        Document func;
        Document morada;
        MongoCollection<Document> funcColl = db.getCollection("funcionario");
        for (Funcionario f : funcionarios.values()) {
            func = new Document();
            func.put("id",f.getId());
            func.put("nome", f.getNome());
            func.put("datanascimento", new Date());
            try {
                java.util.Date date = java.sql.Date.valueOf(f.getData_nascimento());
            } catch (Exception e) {
                e.printStackTrace();
            }
            morada = new Document();
            morada.put("localidade", f.getLocalidade() );
            morada.put("rua", f.getRua());
            morada.put("codigopostal",f.getCp());
            func.put("morada", morada);
            func.put("telefone", f.getTelefone());
            func.put("vencimento", f.getVencimento());
            func.put("email", f.getEmail());
            funcColl.insertOne(func);
        }

        c.close();
    }
    public void setVendas() {
        MongoConnect  con =  new MongoConnect();
        MongoDatabase db = con.getDb();

        Document venda;
        Document item;
        List<Document> items = new ArrayList<>();
        MongoCollection<Document> vendaColl = db.getCollection("vendas");
        for (Venda v : vendas.values()) {
            venda = new Document();
            venda.put("id",v.getId());
            venda.put("data", new Date());
            try {
                java.util.Date date = java.sql.Date.valueOf(v.getData());
            } catch (Exception e) {
                e.printStackTrace();
            }
            venda.put("cupao", v.getCupao());
            venda.put("total", v.getTotal());
            venda.put("funcionario", v.getId_func());
            venda.put("cliente", v.getId_cliente());
            for(Item i: v.getItems()) {
                item = new Document();
                item.put("isbn", i.getIsbn());
                item.put("isbn", i.getTitulo());
                item.put("quantidade", i.getQuantidade());
                item.put("preco", i.getPreco());
                items.add(item);
            }
            venda.put("items",items);
            vendaColl.insertOne(venda);
        }

        con.close();
    }
    public void setClientes() {
        MongoConnect  con =  new MongoConnect();
        MongoDatabase db = con.getDb();

        Document cliente;
        Document morada;
        MongoCollection<Document> funcColl = db.getCollection("clientes");
        for (Cliente c : clientes.values()) {
            cliente = new Document();
            cliente.put("id",c.getId());
            cliente.put("nome", c.getNome());
            cliente.put("datanascimento", new Date());
            try {
                java.util.Date date = java.sql.Date.valueOf(c.getData_nascimento());
            } catch (Exception e) {
                e.printStackTrace();
            }
            morada = new Document();
            morada.put("localidade", c.getLocalidade() );
            morada.put("rua", c.getRua());
            morada.put("codigopostal",c.getCp());
            cliente.put("morada", morada);
            cliente.put("telefone", c.getTelefone());
            cliente.put("email", c.getEmail());
            funcColl.insertOne(cliente);
        }

        con.close();
    }

    public void setEscritores() {
        MongoConnect  con =  new MongoConnect();
        MongoDatabase db = con.getDb();

        Document cliente;
        MongoCollection<Document> funcColl = db.getCollection("escritores");
        for (Escritor e : escritores.values()) {
            cliente = new Document();
            cliente.put("id",e.getId());
            cliente.put("nome", e.getNome());
            cliente.put("nacionalidade", e.getNacionalidade());
            cliente.put("datanascimento", new Date());
            try {
                java.util.Date date = java.sql.Date.valueOf(e.getData_nascimento());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            cliente.put("biografia", e.getBiografia());
            funcColl.insertOne(cliente);
        }

        con.close();
    }
    public Map<Integer, Escritor> getEscritores(){
        return this.escritores;
    }

    public Map<Integer, Funcionario> getFuncionarios(){
        return this.funcionarios;
    }
    public Map<Integer, Cliente> getClientes(){
        return this.clientes;
    }
    public Map<Integer, String> getCategorias(){
        return this.categorias;
    }
    public Map<Integer, Livro> getLivros(){
        return this.livros;
    }
    public Map<Integer, Venda> getVendas(){
        return this.vendas;
    }

}
