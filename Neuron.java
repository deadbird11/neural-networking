import java.util.ArrayList;

public class Neuron {
    // synapses connected on left
    private ArrayList<Synapse> inputs;
    // sigmoid value
    private Double val;
    private double bias;
    private double dBias = 0;
    private Double z = null;
    boolean backpropped = false;

    public Neuron() {
        // makes things easier
        inputs = null;
        val = null;
        bias = 0;
    }
    public Neuron(ArrayList<Synapse> _inputs, double _val, double _bias) {
        // might delete this one...val should be input or calculated
        inputs = _inputs;
        val = Double.valueOf(_val);
        bias = _bias;
    }
    public Neuron(ArrayList<Synapse> _inputs, double _bias) {
        inputs = _inputs;
        bias = _bias;
        val = null;
    }
    public Neuron(double _val) {
        // for first layer, no inputs, no bias
        bias = 0;
        inputs = null;
        val = Double.valueOf(_val);
    }
    public double getBias() { return bias; }
    public void setBias(double instead) { bias = instead; }
    
    public double getVal() { return (val != null) ? val.doubleValue() : 0; }
    public void setVal(double instead) { val = instead; }

    // goes back in the network recursively to calculate value
    // saves time checking if value has been calculated (not null)
    public void updateVal() {
        if (val == null) {
            z = bias;
            for (int i = 0; i < inputs.size(); ++i) {
                z += inputs.get(i).getInput();
            }
            val = sigmoid(z);
        }
    }
    public void backprop(double expected) {
        if (!backpropped) {
            backpropped = true;
            double mult = 2 * (val - expected) * dSigmoid(z);
            dBias += mult;
            for (int i = 0; i < inputs.size(); ++i) {
                inputs.get(i).backprop(mult);
            }
        }
    }
    public void innerBackprop(double mult) {
        if (inputs == null || backpropped) {
            return;
        } else {
            backpropped = true;
            dBias += mult * dSigmoid(z);
            for (int i = 0; i < inputs.size(); ++i) {
                inputs.get(i).backprop(mult);
            }
        }
    }
    public void applyChanges() {
        backpropped = false;
        bias += dBias;
        dBias = 0;
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
    private static double dSigmoid(double x) {
        return 1 / (4*Math.pow(Math.cosh(x/2), 2));
    }
}