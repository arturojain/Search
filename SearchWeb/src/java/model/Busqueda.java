package model;

public class Busqueda {
    private int id;
    private String text;
    
    public Busqueda(int id, String text){
        super();
        this.id = id;
    }
    
    public int getId(){
        return this.id;
    }
    
    public String getText(){
        return this.text;
    }
}
