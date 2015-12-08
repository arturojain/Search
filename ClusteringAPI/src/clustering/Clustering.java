/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clustering;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;
import model.Cluster;
import model.DatosBD;

/**
 *
 * @author arturojain
 */
public class Clustering {
    
    public boolean contains(String d1, String id, Vector<Cluster> matrix) {
        //busco el elemento correcto del vector
        for (Cluster matrix1 : matrix) {
            if (matrix1.id.equals(d1)) {
                if (matrix1.id1.equals(id) || matrix1.id2.equals(id)) {
                    return true;
                } else if (matrix1.id1.matches("^c([0-9]|[1-9][0-9]|[1-9][0-9][0-9])$")) {
                    return contains(matrix1.id1, id, matrix);
                } else if (matrix1.id2.matches("^c([0-9]|[1-9][0-9]|[1-9][0-9][0-9])$")) {
                    return contains(matrix1.id2, id, matrix);
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public String isClustered(int d, Vector<Cluster> matrix){
        String clustered = null;
        for(int i=0; i < matrix.size(); i++){
            String ds = String.valueOf(d+1);
            if(matrix.elementAt(i).id1.equals(ds) || matrix.elementAt(i).id2
                    .equals(ds) || contains(matrix.elementAt(i).id1, ds, matrix) || 
                    contains(matrix.elementAt(i).id2, ds, matrix)){
                clustered = matrix.elementAt(i).id;
            }
        }
        return clustered;
    }
    
    public String isClustered(String ds, Vector<Cluster> matrix){
        for(int i=0; i < matrix.size(); i++){
            if(matrix.elementAt(i).id1.equals(ds) || matrix.elementAt(i).id2
                    .equals(ds)){
                return matrix.elementAt(i).id;
            }
        }
        return ds;
    }
    
    public int isSimilar(int d1, float[][] matrix) {
        int maxIndex = 0;
        if(d1 == maxIndex) //si el documento que se busca es el inicial se salta
            maxIndex++;
        for (int i = 0; i < matrix.length; i++) {
            if(i != d1){
                float newnumber = matrix[d1][i];
                if ((newnumber > matrix[d1][maxIndex])) {
                    maxIndex = i;
                }
            }
        }
        return maxIndex;
    }
    
    public Vector<Cluster> clustering(int size, float[][] similitud){
        Clustering cl = new Clustering();
        Vector<Cluster> matrix = new Vector();
        for (int i=0; i < size; i++){
            int similar = cl.isSimilar(i, similitud);
            Cluster c = null;
            String is = String.valueOf(i+1);
            String ss = String.valueOf(similar+1);
            if(i != 0){
                if(cl.isClustered(i, matrix) != null){
                    is = cl.isClustered(i, matrix);
                    if(is.matches("^c([0-9]|[1-9][0-9]|[1-9][0-9][0-9])$")){
                        is = cl.isClustered(is, matrix);
                    }
                }
                if(cl.isClustered(similar, matrix) != null){
                    ss = cl.isClustered(similar, matrix);
                    if(ss.matches("^c([0-9]|[1-9][0-9]|[1-9][0-9][0-9])$")){
                        ss = cl.isClustered(ss, matrix);
                    }
                }
                c = new Cluster("c"+i, is, ss);
            } else {
                c = new Cluster("c"+i, is, ss);
            }
            matrix.add(c);
            System.out.println(matrix.elementAt(i).toString());
        }
        return matrix;
    }

    public static void main(String[] args) throws SQLException, IOException, 
            FileNotFoundException, ClassNotFoundException, InstantiationException, 
            IllegalAccessException {
        Clustering cl = new Clustering();
        //matrix sim
        DatosBD bd = new DatosBD();
        int size = bd.getSize();
        float[][] similitud = new float[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                similitud[i][j] = bd.compareSim(i + 1, j + 1);;
                System.out.print(similitud[i][j] + " ");
            }
            System.out.print("\n");
        }
        Vector<Cluster> matrix = cl.clustering(size, similitud); //el vector ahora se encuentra obsoleto, se puede cambiar por lista
        //dar de alta en bd
        for(Cluster matrix1 : matrix){
            String id = cl.isClustered(matrix1.id, matrix);
            if(id.equals(matrix1.id))
                id = null;
            bd.altaCluster(matrix1.id, matrix1.id1, id);
            if(!matrix1.id1.equals(matrix1.id2)) //solución hard coded (única solución)
                bd.altaCluster(matrix1.id, matrix1.id2, id);
        }
    }
}
