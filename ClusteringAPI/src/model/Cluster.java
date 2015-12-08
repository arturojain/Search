/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author arturojain
 */
public class Cluster {
    public String id;
    public String id1;
    public String id2;
    
    public Cluster(String id, String id1, String id2){
        this.id = id;
        this.id1 = id1;
        this.id2 = id2;
    }
    
    public String toString(){
        return this.id + " " + this.id1 + " " + this.id2;
    }
}
