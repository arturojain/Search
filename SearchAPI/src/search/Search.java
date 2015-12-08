package search;

import java.util.ArrayList;
import model.DatosBD;
import org.tartarus.snowball.ext.englishStemmer;

/**
 *
 * @author arturojain
 */
public class Search {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        ReadFile rf = new ReadFile();
        ArrayList<String> words = rf.readFile();
        DatosBD bd = new DatosBD();
        int doc;
        doc = 0;
        Search s = new Search();
        for (String word : words) {
            if (!word.equals("")) { //no es un espacio en blanco
                try {
                    doc = Integer.parseInt(word);
                    //es documento
                    bd.agregarDocumento(doc, null);
                } catch (java.lang.NumberFormatException e) {
                    //es palabra
                    if(!word.equals("/")) //ignora el s�mbolo
                        bd.agregarPalabra(doc, word, s.lematizar(word)); //debido a que ley� un n�mero ese es el doc actual
                }
            }
        }
        bd.calcularIDF(); //calcula el IDF
        bd.agregarPesoDoc(); //finaliza calculando la sumatoria del peso
    }
//tomado de la p�gina de Porter2
    public String lematizar(String palabra) {
        englishStemmer stemmer = new englishStemmer();
        stemmer.setCurrent(palabra);
        if (stemmer.stem())
            return stemmer.getCurrent();
        else
            return null;
    }
}
