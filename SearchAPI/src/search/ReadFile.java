package search;

import java.io.*;
import java.util.*;
import model.SEARCH;

public class ReadFile {

    public ArrayList<String> words = new ArrayList<>();

    public ArrayList readFile() throws Exception {
        File file = new File(SEARCH.DATOS.FILE);
        if (file.length() != 0) {
            BufferedReader fr = new BufferedReader(new FileReader(file));
            String[] line;
            String str;
            while ((str = fr.readLine()) != null) {
                line = str.split(" ");
                words.addAll(Arrays.asList(line));
            }
        } else {
            System.out.println("File is empty");
        }
        return words;
    }
}
