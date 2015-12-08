package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import org.tartarus.snowball.ext.englishStemmer;

public class DatosBD {

    ArrayList<Busqueda> arregloB = new ArrayList<>();
    ArrayList<Busqueda> arregloB2 = new ArrayList<>();
    ArrayList<Busqueda> arregloB3 = new ArrayList<>();
    ArrayList<Busqueda> arregloB4 = new ArrayList<>();
    ArrayList<Busqueda> arregloBs = new ArrayList<>();

    public ArrayList<Busqueda> buscar(String terms, int repeticiones, int terminos, int op) throws SQLException, IOException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        ArrayList<String> words = new ArrayList<>();
        String[] line;

        line = terms.split(" ");
        words.addAll(Arrays.asList(line));

        Consultas c = new Consultas();
        for (String word : words) {
            if (!word.equals("")) {
                if (op == 0) {
                    String query = "INSERT INTO Consulta (Term, TF) VALUES ('" + word + "', 1) ON DUPLICATE KEY UPDATE `TF` = `TF` + 1;";
                    c.altaBajaCambio(query); //damos de alta en la base de datos
                } else {
                    String query = "INSERT INTO Consulta (Term, TF) VALUES ('" + lematizar(word) + "', 1) ON DUPLICATE KEY UPDATE `TF` = `TF` + 1;";
                    c.altaBajaCambio(query); //damos de alta en la base de datos
                }
            }
        }
        int i = 0;
        while (i < repeticiones) {
            i++;
            if (op == 0) {
                String query2 = "insert into Consulta (select IndiceInv.Term, 1"
                        + " from IndiceInv, Term"
                        + " where DocID = (select IndiceInv.DocID"
                        + " from Consulta, IndiceInv, Term, Relevantes"
                        + " where Consulta.Term = Term.Term"
                        + " and IndiceInv.Term = Term.Term"
                        + " and IndiceInv.DocID = Relevantes.DocID"
                        + " group by IndiceInv.DocID"
                        + " order by sum(Consulta.TF*Term.IDF*IndiceInv.TF*Term.IDF) desc"
                        + " limit 1) and IndiceInv.Term = Term.Term"
                        + " group by Term"
                        + " order by IDF desc"
                        + " limit " + terminos + ")"
                        + " ON DUPLICATE KEY UPDATE `TF` = `TF` + 1;";
                c.altaBajaCambio(query2);
            } else {
                String query2 = "insert into Consulta (select StemIndiceInv.StemTerm, 1"
                        + " from StemIndiceInv, StemTerm"
                        + " where DocID = (select StemIndiceInv.DocID"
                        + " from Consulta, StemIndiceInv, StemTerm, Relevantes"
                        + " where Consulta.Term = StemTerm.StemTerm"
                        + " and StemIndiceInv.StemTerm = StemTerm.StemTerm"
                        + " and StemIndiceInv.DocID = Relevantes.DocID"
                        + " group by StemIndiceInv.DocID"
                        + " order by sum(Consulta.TF*StemTerm.IDF*StemIndiceInv.TF*StemTerm.IDF) desc"
                        + " limit 1) and StemIndiceInv.StemTerm = StemTerm.StemTerm"
                        + " group by StemTerm"
                        + " order by IDF desc"
                        + " limit " + terminos + ")"
                        + " ON DUPLICATE KEY UPDATE `TF` = `TF` + 1;";
                c.altaBajaCambio(query2);
            }

        }
        if (op == 0) {
            String query3 = "select IndiceInv.DocID, sum(Consulta.TF*Term.IDF*IndiceInv.TF*Term.IDF)"
                    + " from Consulta, IndiceInv, Term"
                    + " where Consulta.Term = Term.Term"
                    + " AND IndiceInv.Term = Term.Term"
                    + " group by IndiceInv.DocID"
                    + " order by 2 desc"
                    + " limit 5000;";
            ResultSet r = c.consultar(query3); //obtenemos los resultados
            while (r.next()) {
                //guarda el resultado nuevo
                int id = r.getInt("DocID");
                //String texto = r.getString("Text");
                Busqueda b;
                b = new Busqueda(id, null);
                arregloB.add(b);
            }
        } else {
            String query3 = "select StemIndiceInv.DocID, sum(Consulta.TF*StemTerm.IDF*StemIndiceInv.TF*StemTerm.IDF)"
                    + " from Consulta, StemIndiceInv, StemTerm"
                    + " where Consulta.Term = StemTerm.StemTerm"
                    + " AND StemIndiceInv.StemTerm = StemTerm.StemTerm"
                    + " group by StemIndiceInv.DocID"
                    + " order by 2 desc"
                    + " limit 5000;";
            ResultSet r = c.consultar(query3); //obtenemos los resultados
            while (r.next()) {
                //guarda el resultado nuevo
                int id = r.getInt("DocID");
                //String texto = r.getString("Text");
                Busqueda b;
                b = new Busqueda(id, null);
                arregloB.add(b);
            }
        }

        String query4 = "truncate table Consulta";
        c.altaBajaCambio(query4);
        return arregloB;
    }
    public ArrayList<Busqueda> documento(int id) throws SQLException, IOException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Consultas c = new Consultas();
        String query = "SELECT * FROM Doc WHERE DocID = " + id;
        ResultSet r = c.consultar(query); //obtenemos los resultados
        while (r.next()) {
            //guarda el resultado nuevo
            //String texto = r.getString("Text");
            Busqueda b;
            b = new Busqueda(id, null);
            arregloBs.add(b);
        }
        return arregloBs;
    }
    public String lematizar(String palabra) {
        englishStemmer stemmer = new englishStemmer();
        stemmer.setCurrent(palabra);
        if (stemmer.stem()) {
            return stemmer.getCurrent();
        } else {
            return null;
        }
    }
//
//    public ArrayList<Busqueda> buscar2(String terms) throws SQLException, IOException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
//
//        ArrayList<String> words = new ArrayList<>();
//        String[] line;
//
//        line = terms.split(" ");
//        words.addAll(Arrays.asList(line));
//
//        Consultas c = new Consultas();
//
//        String query = "truncate table PesoConsulta;";
//        c.altaBajaCambio(query); //damos de alta en la base de datos
//
//        for (String word : words) {
//            if (!word.equals("")) {
//                String query1 = "INSERT INTO Consulta (Term, TF) VALUES ('" + word + "', 1) ON DUPLICATE KEY UPDATE TF = TF+1;";
//                c.altaBajaCambio(query1); //damos de alta en la base de datos
//            }
//        }
//        String query2 = "select i.DocID, sum(q.tf * t.idf * i.tf * t.idf) / (dw.peso * qw.peso)"
//                + " from Consulta q, IndiceInv i, Term t, PesoDoc dw, PesoConsulta qw where q.term = t.term AND"
//                + " i.term = t.term AND"
//                + " i.DocID = dw.DocID"
//                + " group by i.DocID"
//                + " order by 2 desc"
//                + " limit 1000;";
//
//        ResultSet r = c.consultar(query2); //obtenemos los resultados
//        while (r.next()) {
//            //guarda el resultado nuevo
//            int id = r.getInt("DocID");
//            //String texto = r.getString("Text");
//            Busqueda b;
//            b = new Busqueda(id, null);
//            arregloB2.add(b);
//        }
//
//        String query3 = "truncate table Consulta";
//        c.altaBajaCambio(query3);
//
//        return arregloB2;
//    }
//
//    public ArrayList<Busqueda> buscar3(String terms) throws SQLException, IOException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
//
//        ArrayList<String> words = new ArrayList<>();
//        String[] line;
//
//        line = terms.split(" ");
//        words.addAll(Arrays.asList(line));
//
//        Consultas c = new Consultas();
//        for (String word : words) {
//            if (!word.equals("")) {
//                String query = "INSERT INTO Consulta (Term, TF) VALUES ('" + word + "', 1) ON DUPLICATE KEY UPDATE `TF` = `TF` + 1;";
//                c.altaBajaCambio(query); //damos de alta en la base de datos
//            }
//        }
//        String query2 = "select i.DocID, (log(q.tf+1)*t.idf)/sum((log(i.tf+1)*t.idf))"
//                + " from Consulta q, IndiceInv i, Term t"
//                + " where q.Term = t.Term"
//                + " AND i.Term = t.Term"
//                + " group by i.DocID"
//                + " order by 2 desc"
//                + " limit 1000;";
//
//        ResultSet r = c.consultar(query2); //obtenemos los resultados
//        while (r.next()) {
//            //guarda el resultado nuevo
//            int id = r.getInt("DocID");
//            //String texto = r.getString("Text");
//            Busqueda b;
//            b = new Busqueda(id, null);
//            arregloB3.add(b);
//        }
//
//        String query3 = "truncate table Consulta";
//        c.altaBajaCambio(query3);
//
//        return arregloB3;
//    }
//
//    public ArrayList<Busqueda> buscar4(String terms) throws SQLException, IOException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
//
//        ArrayList<String> words = new ArrayList<>();
//        String[] line;
//
//        line = terms.split(" ");
//        words.addAll(Arrays.asList(line));
//
//        Consultas c = new Consultas();
//
//        String query = "truncate table PesoConsulta;";
//        c.altaBajaCambio(query); //damos de alta en la base de datos
//
//        for (String word : words) {
//            if (!word.equals("")) {
//                String query1 = "INSERT INTO Consulta (Term, TF) VALUES ('" + word + "', 1) ON DUPLICATE KEY UPDATE TF = TF+1;";
//                c.altaBajaCambio(query1); //damos de alta en la base de datos
//            }
//        }
//        String query2 = "select i.DocID, ((log(q.tf+1)*t.idf)/sum((log(i.tf+1)*t.idf))) / (dw.peso2 * qw.peso2)"
//                + " from Consulta q, IndiceInv i, Term t, PesoDoc dw, PesoConsulta qw where q.term = t.term AND"
//                + " i.term = t.term AND"
//                + " i.DocID = dw.DocID"
//                + " group by i.DocID"
//                + " order by 2 desc"
//                + " limit 1000;";
//
//        ResultSet r = c.consultar(query2); //obtenemos los resultados
//        while (r.next()) {
//            //guarda el resultado nuevo
//            int id = r.getInt("DocID");
//            //String texto = r.getString("Text");
//            Busqueda b;
//            b = new Busqueda(id, null);
//            arregloB4.add(b);
//        }
//
//        String query3 = "truncate table Consulta";
//        c.altaBajaCambio(query3);
//
//        return arregloB4;
//    }
}