import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Double> inputs = new ArrayList<Double>();
        for (int i = 0; i < 10; ++i) inputs.add(Double.valueOf(i));
        NeuralNetwork nn = new NeuralNetwork(inputs, 2, 16, 10);
        nn.display();
    }
}