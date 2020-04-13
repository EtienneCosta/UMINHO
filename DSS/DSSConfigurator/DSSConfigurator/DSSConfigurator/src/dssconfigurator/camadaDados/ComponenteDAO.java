package dssconfigurator.camadaDados;

import dssconfigurator.camadaLogica.Categoria;
import dssconfigurator.camadaLogica.Componente;
import Exceptions.ComponenteInexistenteException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ComponenteDAO {

    public Componente get(String id) {
        try {

            Componente comp = null;
            Connection c = Connect.connect();

            if (c != null) {
                PreparedStatement ps = c.prepareStatement("SELECT * FROM `Componente` WHERE `idComponente` = ?");
                PreparedStatement psDep = c.prepareStatement("SELECT * FROM ComponentesDependentes WHERE Componente = ?");
                PreparedStatement psInc = c.prepareStatement("SELECT * FROM ComponentesIncompativeis WHERE Componente = ?");
                int idComponente = Integer.parseInt(id);
                ps.setInt(1, idComponente);
                psDep.setInt(1, idComponente);
                psInc.setInt(1, idComponente);
                ResultSet rs = ps.executeQuery();
                ResultSet rsDep = psDep.executeQuery();
                ResultSet rsInc = psInc.executeQuery();

                if (rs.next()) {
                    comp = new Componente();

                    List<String> dep = new ArrayList<>();
                    List<String> inc = new ArrayList<>();

                    PreparedStatement psCat = c.prepareStatement("SELECT * FROM `Categoria` WHERE `idCategoria` = ?");
                    psCat.setInt(1, rs.getInt("Categoria"));
                    ResultSet rsCat = psCat.executeQuery();

                    comp.setId(Integer.toString(rs.getInt("idComponente")));
                    comp.setNome(rs.getString("Nome"));
                    comp.setDescrição(rs.getString("Descricao"));
                    comp.setStock(rs.getInt("Stock"));
                    comp.setPreco(rs.getDouble("Valor"));

                    if (rsCat.next()) {
                        comp.setCategoria(Categoria.valueOf(rsCat.getString("Categoria").toUpperCase()));
                    }

                    while (rsDep.next()) {
                        dep.add(String.valueOf(rsDep.getInt("Dependente")));
                    }

                    while (rsInc.next()) {
                        inc.add(String.valueOf(rsInc.getInt("Incompativel")));
                    }

                    comp.setDependentes(dep);
                    comp.setIncompativeis(inc);

                    c.close();

                    return comp;
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

    public List<Componente> list() {
        try {

            Connection c = Connect.connect();

            List<Componente> res = new ArrayList<>();

            if (c != null) {
                PreparedStatement ps = c.prepareStatement("SELECT * FROM `Componente` AS C INNER JOIN `Categoria` AS CA ON C.Categoria = CA.idCategoria");
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Componente comp = new Componente();

                    comp.setId(Integer.toString(rs.getInt("idComponente")));
                    comp.setNome(rs.getString("Nome"));
                    comp.setDescrição(rs.getString("Descricao"));
                    comp.setStock(rs.getInt("Stock"));
                    comp.setPreco(rs.getDouble("Valor"));
                    comp.setCategoria(Categoria.valueOf(rs.getString("CA.Categoria").toUpperCase()));

                    res.add(comp);

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

    public double getPreco(String id) {
        try {

            double p;
            Connection c = Connect.connect();

            if (c != null) {
                PreparedStatement ps = c.prepareStatement("SELECT Valor FROM `Componente` WHERE `idComponente` = ?");
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    p = rs.getDouble("Valor");

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
        return 0;
    }

    public boolean containsKey(String id) throws NullPointerException {
        try {
            Connection c = Connect.connect();
            PreparedStatement ps = c.prepareStatement("SELECT Nome FROM `Componente` WHERE `idComponente` = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());

        }

        return false;
    }

    public String getNome(String id) {
        try {

            String s;
            Connection c = Connect.connect();

            if (c != null) {
                PreparedStatement ps = c.prepareStatement("SELECT Nome FROM `Componente` WHERE `idComponente` = ?");
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    s = rs.getString("Nome");

                    c.close();

                    return s;
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

    public Categoria getCategoria(String id) {
        try {

            Categoria cat;
            Connection c = Connect.connect();

            if (c != null) {
                PreparedStatement ps = c.prepareStatement("SELECT CA.Categoria FROM `Componente` AS C INNER JOIN `Categoria` AS CA ON C.Categoria = CA.idCategoria WHERE `idComponente` = ?");
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    cat = Categoria.valueOf(rs.getString("CA.Categoria").toUpperCase());

                    c.close();

                    return cat;
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

    public void updateStock(String key, int stock) {

        try {
            Connection c = Connect.connect();
            PreparedStatement ps = c.prepareStatement("UPDATE Componente SET Stock =? WHERE idComponente=?;");
            ps.setInt(1, stock);
            ps.setInt(2, Integer.parseInt(key));
            ps.executeUpdate();

            c.close();

        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());

        }

    }
}
