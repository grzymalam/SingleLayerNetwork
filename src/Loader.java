import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Loader {
    private double subStringSize;
    Loader() {}
    public ArrayList<Vector> load(String path){
        String ASCIIletters = "abcdefghijklmnopqrstuvwxyz";
        LinkedHashMap<String, Double> data = new LinkedHashMap<>();
        ArrayList<Vector> vectors = new ArrayList<>();
        String file = "";
        File languageFolder = new File(path);
        for (File f : languageFolder.listFiles()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(f));
                String line;
                while ((line = br.readLine()) != null) {
                    file += line;
                }
                file = file.toLowerCase();
            }  catch (IOException e) {
                e.printStackTrace();
            }
            Vector vector = getASCIICount(file);
            vectors.add(vector);
            for(char c: ASCIIletters.toCharArray()){
                data.put(String.valueOf(c), 0.0);
            }
            file = "";
        }

        return vectors;
    }
    //returns a normalized vector with the count of each ASCII character
    public Vector getASCIICount(String file){
        String ASCIIletters = "abcdefghijklmnopqrstuvwxyz";
        LinkedHashMap<String, Double> data = new LinkedHashMap<>();
        for(char c: ASCIIletters.toCharArray()){
            data.put(String.valueOf(c), 0.0);
        }
        for(char c: file.toCharArray()){
            if (data.containsKey(String.valueOf(c)))
                data.put(String.valueOf(c), data.get(String.valueOf(c)) + 1);
        }
        Vector vector = new Vector(data.values());
        vector.normalize();
        return vector;
    }
}
