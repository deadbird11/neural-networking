import java.util.ArrayList;
import java.util.Random;

public class NeuralNetwork {
    private ArrayList<Double> inputs;
    private ArrayList<ArrayList<Neuron>> hiddenLayers;
    private ArrayList<Neuron> outputs;

    public NeuralNetwork(ArrayList<Double> _inputs, int numLayers, int layerHeight, int numOutputs) {
        inputs = _inputs;
        hiddenLayers = new ArrayList<ArrayList<Neuron>>();
        Random rand = new Random();
        for (int i = 0; i < numLayers; ++i) {
            ArrayList<Neuron> column = new ArrayList<Neuron>();
            for (int j = 0; j < layerHeight; ++j) {
                ArrayList<Synapse> synapses = new ArrayList<Synapse>();
                Neuron current = new Neuron(synapses, rand.nextDouble() * 10 - 5);
                int prevHeight = (i == 0) ? inputs.size() : layerHeight;

                for (int prev = 0; prev < prevHeight; ++prev) {
                    Neuron from = (i == 0) ? 
                        new Neuron(inputs.get(prev)) : 
                        hiddenLayers.get(i - 1).get(prev);
                    synapses.add(new Synapse(from, current, rand.nextDouble() * 10 - 5));
                }
                column.add(current);
            }
            hiddenLayers.add(column);
        }
        outputs = new ArrayList<Neuron>();
        for (int j = 0; j < numOutputs; ++j) {
            ArrayList<Synapse> synapses = new ArrayList<Synapse>();
            Neuron current = new Neuron(synapses, rand.nextDouble() * 10 - 5);
            int i = hiddenLayers.size()-1;
            for (int prev = 0; prev < hiddenLayers.get(i).size(); ++prev)
                synapses.add(new Synapse(hiddenLayers.get(i).get(prev), 
                                         current, rand.nextDouble() * 10 - 5));
            outputs.add(current);
        }
    }

    public double cost(ArrayList<Double> desiredOutputs) {
        double sum = 0;
        for (int i = 0; i < outputs.size(); ++i) {
            sum += Math.pow(outputs.get(i).getVal() - desiredOutputs.get(i), 2);
        }
        return sum;
    }

    public void run() {
        // change this to take inputs
        for (int j = 0; j < outputs.size(); ++j) {
            outputs.get(j).updateVal();
        }
    }

    public void display() {
        for (int j = 0; j < hiddenLayers.get(0).size(); ++j) {
            if (j < inputs.size()) {
                System.out.printf("%-3.1f", inputs.get(j));
            } else {
                System.out.print("   ");
            }
            for (int i = 0; i < hiddenLayers.size(); ++i) {
                System.out.printf(" ----> %-3.1f", hiddenLayers.get(i).get(j).getVal());
            }
            System.out.println();
        }
        System.out.println("Results...");
        System.out.println();
        
        for (int j = 0; j < outputs.size(); ++j) {
            System.out.printf("|| %-3.1f ||", outputs.get(j).getVal());
        }
    }

    


}