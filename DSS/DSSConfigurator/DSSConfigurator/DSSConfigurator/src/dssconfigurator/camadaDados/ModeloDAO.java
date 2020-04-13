package dssconfigurator.camadaDados;

import dssconfigurator.camadaLogica.Categoria;
import Exceptions.ComponenteInexistenteException;
import dssconfigurator.camadaLogica.Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModeloDAO {

    public Modelo get(String id){
        try{
            Connection c = Connect.connect();

            if(c != null){
                PreparedStatement ps = c.prepareStatement("SELECT * FROM `Modelo` WHERE `idModelo` = ?");
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();

                if(rs.next()){
                    Modelo m = new Modelo();

                    PreparedStatement psComp = c.prepareStatement("SELECT Cat.Categoria, C.idComponente FROM ModeloComponente AS MC\n" +
                            "                        INNER JOIN Componente AS C ON MC.Componente = C.idComponente\n" +
                            "                        INNER JOIN Categoria as Cat ON Cat.idCategoria = C.Categoria\n" +
                            "                        WHERE MC.Modelo = ?;");
                    psComp.setString(1, id);
                    ResultSet rsComp = psComp.executeQuery();
                    Map<Categoria, List<String>> catComp = new HashMap<>();
                    
                    PreparedStatement psPac = c.prepareStatement("SELECT IdPacote FROM ModeloPacote WHERE IdModelo = ?;");
                    psPac.setString(1, id);
                    ResultSet rsPac = psPac.executeQuery();
                    List<String> idPacotes = new ArrayList<>();

                    m.setId(Integer.toString(rs.getInt("idModelo")));
                    m.setNome(rs.getString("Nome"));

                    while(rsComp.next()){

                        Categoria cat = Categoria.valueOf(rsComp.getString("Categoria").toUpperCase());

                        if(catComp.containsKey(cat)){
                            List<String> list = catComp.get(cat);
                            list.add(Integer.toString(rsComp.getInt("idComponente")));
                            catComp.put(cat, list);
                        }
                        else{
                            List<String> list = new ArrayList<>();
                            list.add(Integer.toString(rsComp.getInt("idComponente")));
                            catComp.put(cat, list);
                        }
                    }
                    
                    while(rsPac.next()){

                        String idPacote = rsPac.getString("IdPacote");

                        idPacotes.add(idPacote);
                    }

                    m.setCompDisponiveis(catComp);
                    m.setPacotesDisponiveis(idPacotes);

                    c.close();

                    return m;
                }
            }
        }
        catch(Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }

        return null;
    }
    
    public double getPreco(String id){
        try {

            double p;
            Connection c = Connect.connect();

            if(c != null){
                PreparedStatement ps = c.prepareStatement("SELECT Valor FROM Modelo WHERE idModelo = ?");
                ps.setInt(1, Integer.parseInt(id));
                ResultSet rs = ps.executeQuery();

                if(rs.next()){
                    
                    p = rs.getDouble("Valor");

                    c.close();

                    return p;
                }
                
            }

        }
        catch(Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
        return 0;
    }
    
    /*
    public List<String> getAllOptionalComponentes(String id){
        try{
            Connection c = Connect.connect();

            if(c != null){
                PreparedStatement ps = c.prepareStatement("SELECT * FROM `Modelo` WHERE `idModelo` = ?");
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();

                if(rs.next()){

                    PreparedStatement psComp = c.prepareStatement("SELECT Cat.Categoria, C.idComponente FROM ModeloComponente AS MC\n" +
                            "                        INNER JOIN Componente AS C ON MC.Componente = C.idComponente\n" +
                            "                        INNER JOIN Categoria as Cat ON Cat.idCategoria = C.Categoria\n" +
                            "                        WHERE MC.Modelo = ?;");
                    psComp.setString(1, id);
                    ResultSet rsComp = psComp.executeQuery();
                    


                    while(rsComp.next()){

                        String cat = rsComp.getString("Categoria");

                        if(catComp.containsKey(cat)){
                            List<String> list = catComp.get(cat);
                            list.add(Integer.toString(rsComp.getInt("idComponente")));
                            catComp.put(cat, list);
                        }
                        else{
                            List<String> list = new ArrayList<>();
                            list.add(Integer.toString(rsComp.getInt("idComponente")));
                            catComp.put(cat, list);
                        }
                    }
                    
                    while(rsPac.next()){

                        String idPacote = rsPac.getString("IdPacote");

                        idPacotes.add(idPacote);
                    }

                    m.setCompDisponiveis(catComp);
                    m.setPacotesDisponiveis(idPacotes);

                    c.close();

                    return m;
                }
            }
        }
        catch(Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }

        return null;
    }*/
}
