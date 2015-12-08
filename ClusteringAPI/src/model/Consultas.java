package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Consultas {

    Conexion c = new Conexion();
    Connection con;

    public void altaBajaCambio(String query) throws SQLException, IOException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        try (Connection con = c.getConnection()) {
            if(con == null){
                System.out.println("No hay conexion");
            }else{
                Statement st = con.createStatement();
                st.executeUpdate(query);
            }
            con.close();
        }
    }

    public ResultSet consultar(String query) throws SQLException, IOException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        con = c.getConnection();
        ResultSet rs = null;
        if(con == null){
            System.out.println("No hay conexion");
        }else{
            Statement st = con.createStatement();
            rs = st.executeQuery(query);
        }
        return rs;
    }
}