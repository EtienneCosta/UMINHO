package Data;

import java.time.LocalDate;
import java.util.List;

public class Venda {
    private int id;
    private LocalDate data;
    private double cupao;
    private double total;
    private int id_func;
    private int id_cliente;
    private List<Item> items;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getCupao() {
        return cupao;
    }

    public void setCupao(double cupao) {
        this.cupao = cupao;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getId_func() {
        return id_func;
    }

    public void setId_func(int id_func) {
        this.id_func = id_func;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Venda(int id, LocalDate d, double cupao, double total, int id_func, int id_cliente, List<Item> items){
        this.id = id;
        this.data = d;
        this.cupao = cupao;
        this.total = total;
        this.id_func = id_func;
        this.id_cliente = id_cliente;
        this.items = items;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Id: " + id + "\n");
        sb.append("Data: " + data + "\n");
        sb.append("Cupão: " + cupao + "\n");
        sb.append("Total: " + total + "\n");
        sb.append("ID Funcionario: " + id_func + "\n");
        sb.append("ID Cliente: " + id_cliente + "\n");
        sb.append(items.toString());
        return sb.toString();
    }
}
