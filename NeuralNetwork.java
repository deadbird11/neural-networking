import java.util.ArrayList;
import java.util.Random;

public class NeuralNetwork {
    // just to keep track of stuff and for display
    private ArrayList<Double> inputs = new ArrayList<Double>();
    private ArrayList<ArrayList<Neuron>> hiddenLayers;
    private ArrayList<Neuron> outputs;
    private int desired;

    public NeuralNetwork(int numInputs, int numLayers, int layerHeight, int numOutputs) {
        
        hiddenLayers = new ArrayList<ArrayList<Neuron>>();
        Random rand = new Random();

        for (int i = 0; i < numLayers; ++i) {
            ArrayList<Neuron> column = new ArrayList<Neuron>();

            for (int j = 0; j < layerHeight; ++j) {
                ArrayList<Synapse> synapses = new ArrayList<Synapse>();
                Neuron current = new Neuron(synapses, rand.nextDouble() * 10 - 5);
                // if we are in first layer, we are taking input
                int prevHeight = (i == 0) ? numInputs : layerHeight;
                // giving each neuron its parents from previous layer
                for (int prev = 0; prev < prevHeight; ++prev) {
                    // haven't taken input, so first layer has null parent neurons
                    Neuron from = (i == 0) ? 
                        null : 
                        hiddenLayers.get(i - 1).get(prev);
                    synapses.add(new Synapse(from, current, rand.nextDouble() * 10 - 5));
                }
                column.add(current);
            }
            hiddenLayers.add(column);
        }
        // creating final output layer
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
    // evaluates performance of network
    public double cost(ArrayList<Double> desiredOutputs) {
        double sum = 0;
        for (int i = 0; i < outputs.size(); ++i) {
            sum += Math.pow(outputs.get(i).getVal() - desiredOutputs.get(i), 2);
        }
        return sum;
    }

    public boolean run(ArrayList<Double> _inputs) {
        // filling in the input layer
        // this means filling in parent neurons in every synapse
        inputs = _inputs;
        for (int j = 0; j < hiddenLayers.get(0).size(); ++j) {
            ArrayList<Neuron> parents = new ArrayList<Neuron>();
            for (int prev = 0; prev < _inputs.size(); ++prev) {
                parents.add(new Neuron(_inputs.get(prev).doubleValue()));
            }
            hiddenLayers.get(0).get(j).setParents(parents);
        }
        // going backwards through the network recursively updating nodes
        // this calculates final value
        Integer maxIndex = null;
        double maxVal = 0;
        for (int j = 0; j < outputs.size(); ++j) {
            outputs.get(j).updateVal();
            if (outputs.get(j).getVal() > maxVal) {
                maxVal = outputs.get(j).getVal();
                maxIndex = Integer.valueOf(j);
            }
        }
        return (maxIndex == null) ? false : maxIndex + 1 == desired;
    }
    // formatting stuff
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
    public void backprop() {
        ArrayList<Neuron> outputLayer = hiddenLayers.get(hiddenLayers.size()-1);
        for (int i = 0; i < outputLayer.size(); ++i) {
            outputLayer.get(i).backprop(desired);
        }
    }
    public void trainStep(ArrayList<Double> _inputs, /*DELETE THIS*/ int _desired) {
        // TODO: GET DESIRED FROM INPUT
        desired = _desired;
        String success = (run(_inputs)) ? "Successful" : "Failed";
        display();
        System.out.println("------> Success status: " + success);
        
    }
}