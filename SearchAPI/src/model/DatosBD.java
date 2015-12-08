/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author arturojain
 */
public class DatosBD {

    public void agregarPalabra(int doc, String term, String stemTerm) throws SQLException, IOException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Consultas c = new Consultas();
        String query = "INSERT IGNORE INTO Search.Term (Term, IDF) VALUES ('" + term + "',1);";
        c.altaBajaCambio(query);
        String query2 = "INSERT IGNORE INTO Search.IndiceInv (DocID, Term, TF) VALUES (" + doc + ",'" + term + "',1) ON DUPLICATE KEY UPDATE `TF` = `TF` + 1;";
        c.altaBajaCambio(query2);
        String query3 = "INSERT IGNORE INTO Search.StemTerm (StemTerm, IDF) VALUES ('" + stemTerm + "',1);";
        c.altaBajaCambio(query3);
        String query4 = "INSERT IGNORE INTO Search.StemIndiceInv (DocID, StemTerm, TF) VALUES (" + doc + ",'" + stemTerm + "',1) ON DUPLICATE KEY UPDATE `TF` = `TF` + 1;";
        c.altaBajaCambio(query4);
    }

    public void agregarDocumento(int doc, String text) throws SQLException, IOException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Consultas c = new Consultas();
        String query = "INSERT IGNORE INTO Search.Doc (DocID) VALUES (" + doc + ");";
        c.altaBajaCambio(query);
    }

    public void agregarPesoDoc() throws SQLException, IOException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Consultas c = new Consultas();
        String query = "insert into PesoDoc"
                + " select DocID, sqrt(sum(i.tf * t.idf * i.tf * t.idf)),sqrt((log(i.tf+1)*t.idf)/sum((log(i.tf+1)*t.idf))) from IndiceInv i, Term t"
                + " where i.term = t.term"
                + " group by DocID";
        c.altaBajaCambio(query);
    }

    public void calcularIDF() throws SQLException, IOException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Consultas c = new Consultas();
        String query = "update Term"
                + " set IDF = (SELECT LOG10((select count(*) from Doc)/(select count(*) from IndiceInv where Term.Term = IndiceInv.Term)));";
        c.altaBajaCambio(query);
//        String query2 = "SET SQL_SAFE_UPDATES=0;"
//                + " update StemTerm"
//                + " set IDF = (SELECT LOG10((select count(*) from Doc)/(select count(*) from StemIndiceInv where StemTerm.StemTerm = StemIndiceInv.StemTerm)));";
//        c.altaBajaCambio(query2);
    }
}
