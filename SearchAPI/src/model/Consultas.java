/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author arturojain
 */
public class Consultas {

    Conexion c = new Conexion();

    public void altaBajaCambio(String query) throws SQLException, IOException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        try (Connection con = c.getConnection()) {
            if (con == null) {
                System.out.println("No hay conexion");
            } else {
                Statement st = con.createStatement();
                st.execute(query);
            }
        }
    }

    public ResultSet consultar(String query) throws SQLException, IOException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        ResultSet rs;
        try (Connection con = c.getConnection()) {
            rs = null;
            if (con == null) {
                System.out.println("No hay conexion");
            } else {
                Statement st = con.createStatement();
                rs = st.executeQuery(query);
            }
        }
        return rs;
    }
}
