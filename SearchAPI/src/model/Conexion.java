/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private String USERNAME = "";
    private String IP = "";
    private String DB = "";

    public Connection getConnection() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        USERNAME = SEARCH.DATOS.USERNAME;
        IP = SEARCH.DATOS.IP;
        DB = SEARCH.DATOS.DB;

        Connection conn = null;
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        conn = DriverManager.getConnection("jdbc:mysql://"+IP+"/"+DB+"?user="+USERNAME);
        return conn;
    }
}
