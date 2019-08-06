import java.util.ArrayList;
import java.util.Random;

public class NeuralNetwork {
    // just to keep track of stuff and for display
    private ArrayList<Neuron> inputs;
    private ArrayList<ArrayList<Neuron>> hiddenLayers;
    private ArrayList<Neuron> outputs = new ArrayList<Neuron>();
    private int desired;

    public NeuralNetwork(int numInputs, int numLayers, int layerHeight, int numOutputs) {
        Random rand = new Random();
        hiddenLayers = new ArrayList<ArrayList<Neuron>>();
        for (int j = 0; j < numLayers; ++j) {
            ArrayList<Neuron> layer = new ArrayList<Neuron>();
            for (int i = 0; i < layerHeight; ++i) {
                Neuron current = new Neuron();
                // current.setBias(rand.nextDouble()*10 - 5);
                int prevHeight = (j == 0) ? numInputs : layerHeight;
                for (int prev = 0; prev < prevHeight; ++prev) {
                    Neuron from;
                    if (j == 0) {
                        from = null;
                    } else {
                        from = hiddenLayers.get(j-1).get(prev);
                    }
                    current.addInput(new Synapse(from, current, rand.nextDouble()*3 - 1.5));
                }
                layer.add(current);
            }
            hiddenLayers.add(layer);            
        }
        for (int i = 0; i < numOutputs; ++i) {
            ArrayList<Neuron> last = hiddenLayers.get(hiddenLayers.size()-1);
            OutputNeuron current = new OutputNeuron();
            // current.setBias(rand.nextDouble()*10 - 5);
            for (int prev = 0; prev < last.size(); ++prev) {
                current.addInput(new Synapse(last.get(prev), current, rand.nextDouble()*3 - 1.5));
            }
            outputs.add(current);
        }
    }
    // eactivationuates performance of network
    public double cost(ArrayList<Double> desiredOutputs) {
        double sum = 0;
        for (int i = 0; i < outputs.size(); ++i) {
            sum += Math.pow(outputs.get(i).getActivation() - desiredOutputs.get(i), 2);
        }
        return sum / inputs.size();
    }

    public boolean run(ArrayList<Double> _inputs) {
        // filling in the input layer
        // this means filling in parent neurons in every synapse
        inputs = new ArrayList<Neuron>();
        for (int i = 0; i < _inputs.size(); ++i) {
            inputs.add(new Neuron(_inputs.get(i)));
        }
        for (int j = 0; j < hiddenLayers.get(0).size(); ++j) {
            hiddenLayers.get(0).get(j).setParents(inputs);
        }
        // going backwards through the network recursively updating nodes
        // this calculates final activationue
        int maxIndex = 0;
        double maxactivation = 0;
        for (int j = 0; j < outputs.size(); ++j) {
            outputs.get(j).updateNetwork();
            if (outputs.get(j).getActivation() > maxactivation) {
                maxactivation = outputs.get(j).getActivation();
                maxIndex = j;
            }
        }
        System.out.println();
        System.out.println(maxactivation);
        System.out.println(maxIndex);

        return (maxIndex == -1) ? false : maxIndex == desired;
    }
    // formatting stuff
    public void display() {
        for (int j = 0; j < hiddenLayers.get(0).size(); ++j) {
            if (j < inputs.size()) {
                System.out.printf("%-3.1f", inputs.get(j).getActivation());
            } else {
                System.out.print("   ");
            }
            for (int i = 0; i < hiddenLayers.size(); ++i) {
                System.out.printf(" ----> %-3.1f", hiddenLayers.get(i).get(j).getActivation());
            }
            System.out.println();
        }
        
        quickDisplay();
        
    }
    public void quickDisplay() {
        System.out.println("Results...");
        System.out.println();
        for (int j = 0; j < outputs.size(); ++j) {
            System.out.printf("|| %-3.1f ||", outputs.get(j).getActivation());
        }
    }
    public void backprop() {
        for (int i = 0; i < inputs.size(); ++i) {
            inputs.get(i).backprop();
        }
        for (int i = 0; i < inputs.size(); ++i) {
            inputs.get(i).applyChanges();
        }
    }
    public void trainStep(ArrayList<Double> _inputs, /*TODO: DELETE THIS*/ int _desired) {
        // TODO: GET DESIRED FROM INPUT
        desired = _desired;
        String success = (run(_inputs)) ? "Successful" : "Failed";

        quickDisplay();
        System.out.println();
        System.out.println("------> Success status: " + success);
        // setting expected, will replace later
        for (int i = 0; i < outputs.size(); ++i) {
            outputs.get(i).setExpected(0);
        }
        outputs.get(0).setExpected(1);
        backprop();        
    }
}