import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        int epochs = 10000;
        double learningRate = 0.01;
        double theta = Math.random() * 2 - 1;

        ArrayList<Entry> trainingSet = new ArrayList<>();
        ArrayList<Perceptron> perceptrons = new ArrayList<>();

        String languageDir = "src/languages/";

        int size = 26;

        Loader loader = new Loader();
        File languageFolder = new File(languageDir);

        //load languages
        for (File file : languageFolder.listFiles()) {
            String language = file.getName();
            ArrayList<Vector> vectors = loader.load(file.getAbsolutePath());

            for (Vector vector : vectors) {
                trainingSet.add(new Entry(language, vector));
            }
            perceptrons.add(new Perceptron(size, learningRate, theta, language));
        }
        //learning
        System.out.println("Learning...");

        for (int i = 0; i < epochs; i++) {
            Collections.shuffle(trainingSet);
            for (Entry entry : trainingSet) {
                for (Perceptron perceptron : perceptrons) {
                    perceptron.learn(entry.getVector(), entry.getLanguage().equals(perceptron.getLanguage()) ? 1 : 0);
                    perceptron.normalizeWeights();
                }
            }
        }

        new Window(600, 800, "SLN", perceptrons);
    }
}
