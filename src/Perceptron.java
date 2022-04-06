import java.util.*;

public class Perceptron {
    private Vector weights;
    private double theta;
    private double learningRate;
    private String language;
    private int size;

    public Perceptron(int size, double learningRate, double theta, String language) {
        ArrayList<Double> wagi = new ArrayList<>();
        for (int i = 0; i < size; i++)
            wagi.add(Math.random() * 10 - 5);

        weights = new Vector(wagi);
        this.language = language;
        this.learningRate = learningRate;
        this.theta = theta;
        this.size = size;
    }

    public void learn(Vector toLearn, int expectedValue) {
        alterWeights(guessLINEAR(toLearn), expectedValue, toLearn);
    }

    public double guessLINEAR(Vector values) {
        double dotProduct = values.dotProduct(weights);
        return dotProduct - theta;
    }

    public boolean isOverTheta(double val) {
        return val > theta;
    }

    //0' = 0 - (d-y)a
    //W' = W + (d-y)aX
    public void alterWeights(double guessedOutput, int correctOutput, Vector x) {
        double error = correctOutput - guessedOutput;
        for (int i = 0; i < x.getSize(); i++) {
            weights.vals.set(i, weights.get(i) + error * learningRate * x.get(i));
        }
        //theta
        theta -= error * learningRate;
    }

    //normalize weights
    public void normalizeWeights() {
        weights.normalize();
    }
    public String getLanguage() {
        return language;
    }
}