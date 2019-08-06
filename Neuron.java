import java.util.ArrayList;

public class Neuron {
    // synapses connected on left
    private ArrayList<Synapse> inputs = new ArrayList<Synapse>();
    private ArrayList<Synapse> outputs = new ArrayList<Synapse>();
    // sigmoid value
    protected double activation;
    private double bias;
    protected double dBias = 0;
    private double z = 0;
    private boolean calculated = false;
    private boolean backpropped = false;
    private double error = 0;

    public Neuron() {
        // makes things easier
        activation= 0;
        bias = 0;
    }
    public Neuron(ArrayList<Synapse> _inputs, double _activation, double _bias) {
        inputs = _inputs;
        activation= _activation;
        bias = _bias;
    }
    public Neuron(ArrayList<Synapse> _inputs, double _bias) {
        inputs = _inputs;
        bias = _bias;
        activation= 0;
    }
    public Neuron(double _activation) {
        // for first layer, no inputs, no bias
        bias = 0;
        activation= _activation;
    }
    public double getBias() { return bias; }
    public void setBias(double instead) { bias = instead; }
    
    public double getActivation() { return activation; }
    public void setActivation(double instead) { activation = instead; }

    // goes back in the network recursively to calculate value
    // saves time checking if value has been calculated
    public void updateNetwork() {
        if (inputs.size() > 0 && !calculated) {
            calculated = true;
            z = bias;
            for (int i = 0; i < inputs.size(); ++i) {
                z += inputs.get(i).getOutput();
            }
            activation= sigmoid(z);
        }
    }
    public void setExpected(double expected) {
        expected = Double.valueOf(expected);
    }
    public void addOutput(Synapse s) {
        outputs.add(s);
    }
    public void addInput(Synapse s) {
        inputs.add(s);
    }
    public double backprop() {
        if (!backpropped) {
            backpropped = true;
            error = 0;
            for (int i = 0; i < outputs.size(); ++i) {
                error += outputs.get(i).backprop();
            }
            error *= dSigmoid();
            dBias = error;
        }
        return error;
    }
    public void applyChanges() {
        backpropped = false;
        calculated = false;
        bias -= dBias;
        dBias = 0;
        for (int i = 0; i < outputs.size(); ++i) {
            outputs.get(i).applyChanges();
        }
    }
    // for changing out the input to the network
    // only the first layer uses this
    public void setParents(ArrayList<Neuron> parents) {
        for (int i = 0; i < inputs.size(); ++i) {
            inputs.get(i).setFrom(parents.get(i));
        }
    }
    // Function: R -> [0, 1]
    private static double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }
    protected double dSigmoid() {
        return activation * (1 - activation);
    }
}