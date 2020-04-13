/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dssconfigurator.camadaDados;

import dssconfigurator.camadaLogica.Configuracao;
import dssconfigurator.camadaLogica.Encomenda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Joana Marta Cruz
 */
public class EncomendaDAO {

    public boolean containsKey(String id) throws NullPointerException {
        try {
            Connect con = new Connect();
            Connection c = con.connect();
            PreparedStatement ps = c.prepareStatement("SELECT Cliente FROM `Encomenda` WHERE `idEncomenda` = ?");
            ps.setInt(1, Integer.parseInt(id));
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public Encomenda get(String id) {
        try {
            Encomenda enc = null;
            Connect con = new Connect();
            Connection c = con.connect();

            if (c != null) {
                PreparedStatement ps = c.prepareStatement("Select * from `Encomenda` where `idEncomenda`=?");

                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    enc = new Encomenda();
                    Configuracao co = new Configuracao();
                    LocalDate ld=rs.getDate("Data").toLocalDate();

                    enc.setId(id);
                    enc.setData(ld);
                    enc.setNomeCliente(rs.getString("Cliente"));
                    enc.setContactoCliente(rs.getString("Contacto"));
                    enc.setValor(rs.getInt("Valor"));
                    enc.setEstado(rs.getString("Estado"));

                    PreparedStatement comp = c.prepareStatement("Select Distinct Componente from `EncomendaComponente` where `Encomenda`=?");
                    PreparedStatement pac = c.prepareStatement("Select Distinct Pacote from `EncomendaPacote` where `Encomenda`=?");
                    comp.setInt(1, rs.getInt("idEncomenda"));
                    pac.setInt(1, rs.getInt("idEncomenda"));
                    ResultSet rcomp = comp.executeQuery();
                    ResultSet encop = pac.executeQuery();

                    co.setIdModelo(Integer.toString(rs.getInt("Modelo")));

                    List<String> cb = new ArrayList<>();
                    List<String> coo = new ArrayList<>();
                    List<String> pa = new ArrayList<>();

                    while (rcomp.next()) {
                        if (rcomp.getInt("Componente") >= 1 && rcomp.getInt("Componente") <= 35) {
                            cb.add(Integer.toString(rcomp.getInt("Componente")));
                        } else {
                            coo.add(Integer.toString(rcomp.getInt("Componente")));
                        }

                    }

                    while (encop.next()) {
                        pa.add(Integer.toString(encop.getInt("Pacote")));
                    }

                    co.setComponentesBase(cb);
                    co.setComponentesOpcionais(coo);
                    co.setPacotes(new ArrayList<>());
                    enc.setConfig(co);

                    return enc;

                }

            } else {
                throw new Exception("Não foi possível efectuar a conexão!");
            }
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public List<Encomenda> list() {
        try {

            Connection c = Connect.connect();

            List<Encomenda> res = new ArrayList<>();

            if (c != null) {
                PreparedStatement ps = c.prepareStatement("SELECT idEncomenda, Data, Cliente, Contacto, Estado FROM `Encomenda`");
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Encomenda enc = new Encomenda();
                    
                    enc.setId(Integer.toString(rs.getInt("idEncomenda")));
                    enc.setData(rs.getDate("Data").toLocalDate());
                    enc.setNomeCliente(rs.getString("Cliente"));
                    enc.setContactoCliente(rs.getString("Contacto"));
                    enc.setEstado(rs.getString("Estado"));

                    res.add(enc);

                }

                c.close();

                return res;
            }

        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());

        }

        return null;
    }

    public void remove(String key) {
        try {
            Connection c = Connect.connect();
            Encomenda e = get(key);
            PreparedStatement ec = c.prepareStatement("Delete from EncomendaComponente where `Encomenda`=?");
            PreparedStatement ep = c.prepareStatement("Delete from EncomendaPacote where `Encomenda`=?");
            PreparedStatement stm = c.prepareStatement("Delete from Encomenda where `idEncomenda`=?");

            ec.setString(1, key);
            ep.setString(1, key);
            stm.setString(1, key);
            ec.executeUpdate();
            ep.executeUpdate();
            stm.executeUpdate();
            c.close();

        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());

        }

    }

    public void put(Encomenda e) {
        try {
            Connection c = Connect.connect();
            PreparedStatement ps = c.prepareStatement("INSERT INTO Encomenda (idEncomenda,Data,Cliente,Contacto,Valor,Estado,FuncionarioS,Modelo) VALUES (?,?,?,?,?,?,?,?);");
            
            Configuracao config = e.getConfig();
            List<String> componentes = config.getComponentesBaseEOpcionais();
            List<String> pacotes = config.getPacotes();
            String idModelo = config.getIdModelo();
            String idFuncionario = config.getIdFuncionario();
            
            LocalDate date = e.getData();
         
            java.sql.Date tem =java.sql.Date.valueOf(date);       
            
            ps.setInt(1, Integer.parseInt(e.getId()));
            ps.setDate(2,tem);
            ps.setString(3, e.getNomeCliente());
            ps.setString(4, e.getContactoCliente());
            ps.setDouble(5, e.getValor());
            ps.setString(6, e.getEstado());
            ps.setString(7, "joanacruz");
            ps.setInt(8, Integer.parseInt(idModelo));
            ps.executeUpdate();
            PreparedStatement p = c.prepareStatement("INSERT INTO EncomendaPacote (Encomenda,Pacote) Values (?,?) ");
            PreparedStatement co = c.prepareStatement("INSERT INTO EncomendaComponente (Encomenda,Componente) Values (?,?) ");

            for (String s : componentes) {
                co.setInt(1, Integer.parseInt(e.getId()));
                co.setInt(2, Integer.parseInt(s));
            }

            for (String s : pacotes) {
                co.setInt(1, Integer.parseInt(e.getId()));
                co.setInt(2, Integer.parseInt(s));
            }

            co.executeUpdate();
            p.executeUpdate();
            c.close();

        } catch (Exception ex) {
            System.err.println("Got an exception!");
            System.err.println(ex.getMessage());

        }
    }

    public int size() {
        Connection c = Connect.connect();
        try {
            int i = 0;
            Statement stm = c.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Encomenda");
            for (; rs.next(); i++);
            c.close();
            return i;
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }
}
