/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dssconfigurator.camadaDados;

import dssconfigurator.camadaLogica.Administrador;
import dssconfigurator.camadaLogica.Funcionario;
import dssconfigurator.camadaLogica.FuncionarioFabrica;
import dssconfigurator.camadaLogica.FuncionarioStand;
import dssconfigurator.camadaLogica.Utilizador;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Joana Marta Cruz
 */
public class UtilizadorDAO {

    public List<Utilizador> list() {
        try {

            Connection c = Connect.connect();

            List<Utilizador> res = new ArrayList<>();

            if (c != null) {
                PreparedStatement psAdmin = c.prepareStatement("Select * FROM Utilizador WHERE Username=?");
                psAdmin.setString(1, "admin");
                ResultSet rsAdmin = psAdmin.executeQuery();
                if (rsAdmin.next()) {
                    Administrador a = new Administrador();
                    a.setUsername(rsAdmin.getString("Username"));
                    a.setPassword(rsAdmin.getString("Password"));

                    res.add(a);
                }

                PreparedStatement psFuncFabrica = c.prepareStatement("SELECT U.Username, U.Password, F.Nome, F.Contacto, F.Morada FROM Utilizador AS U\n"
                        + "                        INNER JOIN Funcionario AS F ON U.Username = F.Username\n"
                        + "                        INNER JOIN FuncionarioFabrica as FF ON FF.FuncionarioF = F.Username\n;");
                ResultSet rsFuncFabrica = psFuncFabrica.executeQuery();

                while (rsFuncFabrica.next()) {
                    FuncionarioFabrica ff = new FuncionarioFabrica();

                    ff.setUsername(rsFuncFabrica.getString("Username"));
                    ff.setPassword(rsFuncFabrica.getString("Password"));
                    ff.setNome(rsFuncFabrica.getString("Nome"));
                    ff.setMorada(rsFuncFabrica.getString("Morada"));
                    ff.setContacto(rsFuncFabrica.getString("Contacto"));

                    res.add(ff);

                }

                PreparedStatement psFuncStand = c.prepareStatement("SELECT U.Username, U.Password, F.Nome, F.Contacto, F.Morada FROM Utilizador AS U\n"
                        + "                        INNER JOIN Funcionario AS F ON U.Username = F.Username\n"
                        + "                        INNER JOIN FuncionarioStand as FS ON FS.FuncionarioS = F.Username\n;");
                ResultSet rsFuncStand = psFuncStand.executeQuery();

                while (rsFuncStand.next()) {
                    FuncionarioStand fs = new FuncionarioStand();

                    fs.setUsername(rsFuncStand.getString("Username"));
                    fs.setPassword(rsFuncStand.getString("Password"));
                    fs.setNome(rsFuncStand.getString("Nome"));
                    fs.setMorada(rsFuncStand.getString("Morada"));
                    fs.setContacto(rsFuncStand.getString("Contacto"));

                    res.add(fs);

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

    public Utilizador get(String username){
        try{

            Connection c = Connect.connect();

            FuncionarioFabrica ff;
            FuncionarioStand fs;

            if(c != null){
                                 
                PreparedStatement psFuncFabrica = c.prepareStatement("SELECT U.Username, U.Password, F.Nome, F.Contacto, F.Morada FROM Utilizador AS U\n" 
                                                    +" INNER JOIN Funcionario AS F ON U.Username = F.Username\n" 
                                                    +"INNER JOIN FuncionarioFabrica as FF ON FF.FuncionarioF = F.Username\n WHERE U.Username=?;");
                psFuncFabrica.setString(1, username);
                ResultSet rsFuncFabrica = psFuncFabrica.executeQuery();

                if(rsFuncFabrica.next()){
                    ff = new FuncionarioFabrica();

                    ff.setUsername(rsFuncFabrica.getString("Username"));
                    ff.setPassword(rsFuncFabrica.getString("Password"));
                    ff.setNome(rsFuncFabrica.getString("Nome"));
                    ff.setMorada(rsFuncFabrica.getString("Morada"));
                    ff.setContacto(rsFuncFabrica.getString("Contacto"));
                    
                    return ff;

                }
                else{
                
                    PreparedStatement psFuncStand = c.prepareStatement("SELECT U.Username, U.Password, F.Nome, F.Contacto, F.Morada FROM Utilizador AS U\n" +
                                "                        INNER JOIN Funcionario AS F ON U.Username = F.Username\n" +
                                "                        INNER JOIN FuncionarioStand as FS ON FS.FuncionarioS = F.Username\n WHERE U.Username=?;");
                    psFuncStand.setString(1, username);
                    ResultSet rsFuncStand = psFuncStand.executeQuery();

                    if(rsFuncStand.next()){
                        fs = new FuncionarioStand();

                        fs.setUsername(rsFuncStand.getString("Username"));
                        fs.setPassword(rsFuncStand.getString("Password"));
                        fs.setNome(rsFuncStand.getString("Nome"));
                        fs.setMorada(rsFuncStand.getString("Morada"));
                        fs.setContacto(rsFuncStand.getString("Contacto"));

                        return fs;

                    }
                }

                c.close();

            }

        }
        catch(Exception e){
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());

        }   

        return null;
    }

    public boolean containsKey(String username) {
        try {
            Connection c = Connect.connect();
            PreparedStatement ps = c.prepareStatement("SELECT Username FROM `Utilizador` WHERE `Username` = ?;");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());

        }

        return false;
    }

    public void put(String key, Funcionario f) {
        try {
            Connection c = Connect.connect();
            PreparedStatement ps = c.prepareStatement("INSERT INTO Utilizador (Username, Password) VALUES (?,?);");
            ps.setString(1, key);
            ps.setString(2, f.getPassword());
            ps.executeUpdate();

            PreparedStatement ps1 = c.prepareStatement("INSERT INTO Funcionario (Username, Nome, Contacto, Morada) VALUES (?,?, ?, ?);");
            ps1.setString(1, key);
            ps1.setString(2, f.getNome());
            ps1.setString(3, f.getContacto());
            ps1.setString(4, f.getMorada());
            ps1.executeUpdate();

            if (f instanceof FuncionarioStand) {
                PreparedStatement ps2 = c.prepareStatement("INSERT INTO FuncionarioStand (FuncionarioS) VALUES (?);");
                ps2.setString(1, key);
                ps2.executeUpdate();
            } else {
                PreparedStatement ps3 = c.prepareStatement("INSERT INTO FuncionarioFabrica (FuncionarioF) VALUES (?);");
                ps3.setString(1, key);
                ps3.executeUpdate();
            }
            
            c.close();

        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());

        }
    }

    public void remove(String key) {
        try {
            Connection c = Connect.connect();
            Funcionario f = (Funcionario)get(key);
            if (f instanceof FuncionarioStand) {
                PreparedStatement stm = c.prepareStatement("Delete from FuncionarioStand where `FuncionarioS`=?");
                stm.setString(1, key);
                stm.executeUpdate();
            } else {
                PreparedStatement stm = c.prepareStatement("Delete from FuncionarioFabrica where `FuncionarioF`=?");
                stm.setString(1, key);
                stm.executeUpdate();
            }
            PreparedStatement stm1 = c.prepareStatement("Delete from Funcionario where `Username`=?");
            stm1.setString(1, key);
            stm1.executeUpdate();

            PreparedStatement stm2 = c.prepareStatement("Delete from Utilizador where `Username`=?");
            stm2.setString(1, key);
            stm2.executeUpdate();
            
            c.close();

        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());

        }

    }

    public void updateInfo(String key, String adress, String contact) {

        try {
            Connection c = Connect.connect();
            PreparedStatement ps = c.prepareStatement("UPDATE Funcionario SET Contacto =?, Morada=? WHERE Username=?;");
            ps.setString(1, contact);
            ps.setString(2, adress);
            ps.setString(3, key);
            ps.executeUpdate();
            
            c.close();

        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());

        }

    }
}
