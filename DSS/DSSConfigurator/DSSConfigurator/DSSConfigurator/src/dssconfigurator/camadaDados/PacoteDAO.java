/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dssconfigurator.camadaDados;

import Exceptions.ComponenteInexistenteException;
import dssconfigurator.camadaLogica.Pacote;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Joana Marta Cruz
 */
public class PacoteDAO {

    public Pacote get(String idPacote) {
        try {

            Pacote p = null;
            Connection c = Connect.connect();

            if (c != null) {
                PreparedStatement ps = c.prepareStatement("SELECT * FROM Pacote WHERE idPacote = ?");
                PreparedStatement psComps = c.prepareStatement("SELECT * FROM PacoteComponente WHERE Pacote = ?");
                ps.setString(1, idPacote);
                psComps.setString(1, idPacote);
                ResultSet rs = ps.executeQuery();
                ResultSet rsComps = psComps.executeQuery();

                if (rs.next()) {
                    p = new Pacote();

                    List<String> comps = new ArrayList<>();

                    p.setId(Integer.toString(rs.getInt("IdPacote")));
                    p.setNome(rs.getString("Nome"));
                    p.setDesconto(rs.getDouble("Desconto"));

                    while (rsComps.next()) {
                        comps.add(Integer.toString(rsComps.getInt("Componente")));
                    }

                    p.setComponentes(comps);

                    c.close();

                    return p;
                } else {
                    throw new ComponenteInexistenteException();
                }
            }

        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public List<String> getComponentes(String idPacote) {
        try {

            Connection c = Connect.connect();
            List<String> comps = new ArrayList<>();

            if (c != null) {
                PreparedStatement psComps = c.prepareStatement("SELECT * FROM PacoteComponente WHERE Pacote = ?");
                psComps.setString(1, idPacote);
                ResultSet rsComps = psComps.executeQuery();

                while (rsComps.next()) {
                    comps.add(Integer.toString(rsComps.getInt("Componente")));
                }

                c.close();

                return comps;
            }

        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public double getDesconto(String id) {
        try {

            double desconto;
            Connection c = Connect.connect();

            if (c != null) {
                PreparedStatement ps = c.prepareStatement("SELECT Desconto FROM `Pacote` WHERE `idPacote` = ?");
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    desconto = rs.getDouble("Desconto");

                    c.close();

                    return desconto;

                }

            }
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
        return 0;
    }

}
