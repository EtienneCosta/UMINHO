/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dssconfigurator.camadaDados;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

/**
 *
 * @author Joana Marta Cruz
 */
public class Connect {

private static final String URL = "localhost:3306";
private static final String SCHEMA = "ConfiguraFacil?useSSL=false";
private static final String USERNAME = "root";
private static final String PASSWORD = "Etiennecosta1";

public static Connection connect() {
    try {
        Class.forName("com.mysql.jdbc.Driver");

        Connection cn =
        DriverManager.getConnection("jdbc:mysql://"+URL+"/"+SCHEMA, USERNAME, PASSWORD);
        return cn;
    } catch (Exception e) {
    //unable to connect
    e.printStackTrace();
    }
    return null;
 }

public static void close(Connection connection) {
    try {
        connection.close();
    } catch (Exception e) {
    //nothing to close
    }
    } 
}

