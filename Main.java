import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        ArrayList<Double> inputs = new ArrayList<Double>();
        ArrayList<Double> outputs = new ArrayList<Double>();
        Random rand = new Random();
        for (int i = 0; i < 10; ++i) {
             inputs.add(rand.nextDouble()+.3);
             outputs.add(Double.valueOf(0));
        }
        outputs.set(0, Double.valueOf(1));
        NeuralNetwork nn = new NeuralNetwork(inputs, 2, 16, 10);
        nn.display();
        System.out.println();
        System.out.println();
        System.out.println();        
        System.out.println("\n\n\n**********************\nRunning...");
        nn.run();
        System.out.println("Done!");
        nn.display();
        System.out.println();
        System.out.println(nn.cost(outputs));
    }
}