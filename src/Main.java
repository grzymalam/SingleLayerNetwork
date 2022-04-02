import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        int epochs = 10000;
        double learningRate = 0.05;
        double steepness = 1;
        double theta = Math.random() * 2 - 1;
        ArrayList<Entry> trainingSet = new ArrayList<>();

        System.out.println("Theta: " + theta);
        ArrayList<Perceptron> perceptrons = new ArrayList<>();
        String languageDir = "src/languages/";
        String ASCIILetters = "abcdefghijklmnopqrstuvwxyz";
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
            for(Entry entry : trainingSet) {
                for (Perceptron perceptron : perceptrons) {
                    perceptron.learn(entry.getVector(), entry.getLanguage().equals(perceptron.getLanguage()) ? 1 : 0);
                }
            }
        }
        String testString = null;
        try {
            testString = Files.readAllLines(new File("src/test.txt").toPath()).get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Vector test = loader.getASCIICount(testString);
        System.out.println("test vector: " + test);
        HashMap<String, Double> results = new HashMap<>();
        for (Perceptron perceptron : perceptrons) {
            double guess = perceptron.guessLINEAR(test);
            results.put(perceptron.getLanguage(), guess);
            System.out.println("LINEAR " + perceptron.getLanguage() + ": " + perceptron.guessLINEAR(test) + " theta: " + perceptron.getTheta());
            System.out.println("SIG " + perceptron.getLanguage() + ": " + guess + " theta: " + perceptron.getTheta());
            System.out.println("weights: " + perceptron.getWeights());
        }
        System.out.println("\n\n");
        System.out.println("guess: " + results.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey());


//        //decision
//        Scanner scanner = new Scanner(System.in);
//        while(true) {
//            System.out.println("1 - enter own vector\n2 - use test vectors\n3 - relearn");
//            int choice = scanner.nextInt();
//            if (choice == 1) {
//                classify(perceptron, activationClass, noActivationClass);
//            } else if (choice == 2) {
//                classifyTestVectors(perceptron, activationClass, noActivationClass, testFile);
//            }else{
//                perceptron.resetWeights();
//                for(int i = 0; i < epochs; i++){
//                    LinkedHashMap<Vector, String> probka = getSubset(scramble(map), sampleSize);
//                    for(Map.Entry<Vector, String> entry: probka.entrySet()){
//                        perceptron.learn(entry.getKey(), entry.getValue().equals(activationClass) ? 1 : 0);
//                    }
//                }
//            }
//        }

    }


    //return a map containing some of the inputed map's entries
    public static LinkedHashMap<Vector, String> getSubset(LinkedHashMap<Vector, String> map, int size) {
        LinkedHashMap<Vector, String> subset = new LinkedHashMap<>();
        for (int i = 0; i < size; i++) {
            subset.put((Vector) map.keySet().toArray()[i], (String) map.values().toArray()[i]);
        }
        return subset;
    }
//    public static void classifyTestVectors(Perceptron perceptron, String activationClass, String noActivationClass, String testPath){
//        Loader loader = new Loader();
//        //test set
//        LinkedHashMap<Vector, String> testMap = loader.load(testPath);
//        double testCount = testMap.size();
//        double successfulGuesses = 0;
//        //testing
//        for(Map.Entry<Vector, String> entry : testMap.entrySet()){
//            Vector vector = entry.getKey();
//            String value = entry.getValue();
//            int prediction = perceptron.predict(vector);
//            String classifiedAs = prediction == 1 ? activationClass : noActivationClass;
//            System.out.println("prediction = " + classifiedAs + " real val = " + value);
//            if (value.equals(classifiedAs)) {
//                successfulGuesses++;
//            }
//        }
//        DecimalFormat df = new DecimalFormat("00.00");
//        System.out.println("success rate = " + df.format(successfulGuesses/testCount*100)+"%");
//        System.out.println("Correct " + successfulGuesses + " of " + testCount);
//        System.out.println("Weights: " + perceptron.getWeights());
//        System.out.println("Theta: " + perceptron.getTheta());
//    }

    //take input from user and classify it
    public static void classify(Perceptron perceptron, String activationClass, String noActivationClass) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a vector: ");
        String input = scanner.nextLine();
        String[] split = input.split(" ");
        double[] vals = Arrays.stream(split).mapToDouble(Double::parseDouble).toArray();
        Vector vector = new Vector(vals);
        double prediction = perceptron.guessLINEAR(vector);
        System.out.println("Prediction: " + (prediction == 1 ? activationClass : noActivationClass));
    }
}
