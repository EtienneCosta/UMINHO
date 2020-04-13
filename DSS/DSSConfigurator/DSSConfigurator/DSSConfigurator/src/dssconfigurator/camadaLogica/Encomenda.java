/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dssconfigurator.camadaLogica;

import java.time.LocalDate;

/**
 *
 * @author joanacruz
 */
public class Encomenda {
    private String id;
    private LocalDate data;
    private double valor;
    private Configuracao config;
    private String estado;
    private String nomeCliente;
    private String contactoCliente;

    public Encomenda(){
        this.id = "";
        this.data = LocalDate.now();
        this.valor = 0;
        this.config = new Configuracao();
        this.estado = "";
        this.nomeCliente = "";
        this.contactoCliente = ""; 
    }
    
    public Encomenda(String id, LocalDate data, double valor, Configuracao config, String estado, String nomeCliente, String contactoCliente) {
        this.id = id;
        this.data = data;
        this.valor = valor;
        this.config = config.clone();
        this.estado = estado;
        this.nomeCliente = nomeCliente;
        this.contactoCliente = contactoCliente;
    }
    
    public Encomenda(Encomenda e){
        this.id = e.getId();
        this.data = e.getData();
        this.valor = e.getValor();
        this.config = e.getConfig();
        this.estado = e.getEstado();
        this.nomeCliente = e.getNomeCliente();
        this.contactoCliente = e.getContactoCliente(); 
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getData() {
        return this.data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getValor() {
        return this.valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Configuracao getConfig() {
        return this.config;
    }

    public void setConfig(Configuracao config) {
        this.config = config;
    }
    
    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getContactoCliente() {
        return contactoCliente;
    }

    public void setContactoCliente(String contactoCliente) {
        this.contactoCliente = contactoCliente;
    }
    
    public String getDetalhes(){
        return ("Encomenda " + this.id);
    }
    
    @Override
    public Encomenda clone() {
        return new Encomenda(this);
    }
}
