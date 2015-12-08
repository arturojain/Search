/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author arturojain
 */
public class DatosBD {

    Consultas c = new Consultas();
    
    public void altaCluster(String id, String id1, String id2) throws SQLException, IOException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException{
        String query = "INSERT INTO Cluster (ClusterID, DocID, ParentID) VALUES ('"+id+"','"+id1+"','"+id2+"')";
        c.altaBajaCambio(query);
    }
    
    public float compareSim(int d1, int d2) throws SQLException, IOException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        float sim = 0;
        String query = "select IndiceInv.DocID, sum(t1.TF*Term.IDF*IndiceInv.TF*Term.IDF) AS Sim"
                + " from IndiceInv, Term, (select Term, DocID, TF from IndiceInv where DocID = "+d1+") AS t1"
                + " where Term.Term = t1.Term"
                + " AND IndiceInv.Term = Term.Term"
                + " AND IndiceInv.DocID =" + d2
                + " group by IndiceInv.DocID";
        
        ResultSet r = c.consultar(query);
        while (r.next()) {
            //guarda el resultado nuevo
            sim = r.getFloat("Sim");
        }
        c.con.close();
        return sim;
    }

    public int getSize() throws SQLException, IOException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        int size = 0;
        ResultSet r = c.consultar("SELECT COUNT(*) AS Size FROM Doc");
        while (r.next()) {
            //guarda el resultado nuevo
            size = r.getInt("Size");
        }
        return size;
    }
}
