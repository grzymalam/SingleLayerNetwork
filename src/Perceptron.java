import java.util.*;

public class Perceptron {
    private Vector weights;
    private double theta;
    private double learningRate;
    private String language;
    private int size;
    public Perceptron(int size,  double learningRate, double theta, String language) {
        ArrayList<Double> wagi = new ArrayList<>();
        for (int i = 0; i < size ; i++)
            wagi.add(Math.random()*10-5);

        weights = new Vector(wagi);
        this.language = language;
        this.learningRate = learningRate;
        this.theta = theta;
        this.size = size;
    }

    public void learn(Vector toLearn, int expectedValue){
        double dotProduct = toLearn.dotProduct(weights);


            alterWeights(guessLINEAR(toLearn), expectedValue, toLearn);

    }
    //1/1+e^-x

    public double guessSIGMOID(Vector values, double steepness){
        return 2/(1 + Math.pow(Math.E, values.dotProduct(weights)*steepness))-1;
    }

    public double guessLINEAR(Vector values){
        double dotProduct = values.dotProduct(weights);
        return dotProduct-theta;
    }
    public boolean isOverTheta(double val){
        return val > theta;
    }

    //0' = 0 - (d-y)a
    //W' = W + (d-y)aX
    public void alterWeights(double guessedOutput, int correctOutput, Vector x){
        double error = correctOutput - guessedOutput;
        for (int i = 0; i < x.getSize(); i++) {
            double toAdd = error * learningRate * x.get(i);
            weights.vals.set(i, weights.get(i) + error * learningRate * x.get(i));
        }
        //theta
        theta -= error * learningRate;
    }
    public void resetWeights(){
        for (int i = 0; i < size ; i++)
            weights.set(i, Math.random()*2-1);
        theta = Math.random()*10-5;
    }
    //normalize weights
    public void normalizeWeights(){
        double sum = 0;
        for (int i = 0; i < weights.getSize(); i++) {
            sum += Math.abs(weights.get(i));
        }
        for (int i = 0; i < weights.getSize(); i++) {
            weights.set(i, weights.get(i)/sum);
        }
    }
    public Vector getWeights(){
        return weights;
    }
    public double getTheta(){
        return theta;
    }

    public String getLanguage() {
        return language;
    }
}

