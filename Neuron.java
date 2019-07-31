import java.util.ArrayList;

public class Neuron {
    // synapses connected on left
    protected ArrayList<Synapse> inputs;
    // sigmoid value
    protected Double val;
    protected double bias;
    private double dBias = 0;
    protected Double z = null;
    private boolean calculated = false;

    public Neuron() {
        // makes things easier
        inputs = null;
        val = null;
        bias = 0;
    }
    public Neuron(ArrayList<Synapse> _inputs, double _val, double _bias) {
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
    // saves time checking if value has been calculated
    public void updateNetwork() {
        if (inputs != null && !calculated) {
            calculated = true;
            z = bias;
            for (int i = 0; i < inputs.size(); ++i) {
                z += inputs.get(i).getInput();
            }
            val = sigmoid(z);
        }
    }
    public void backprop(double expected) {
        if (inputs != null) {
            double mult = 2 * (val - expected) * dSigmoid(z);
            dBias += -1 * mult;
            for (int i = 0; i < inputs.size(); ++i) {
                inputs.get(i).backprop(mult);
            }
        }
    }
    public void innerBackprop(double mult) {
        if (inputs != null) {
            dBias += mult * dSigmoid(z);
            for (int i = 0; i < inputs.size(); ++i) {
                inputs.get(i).backprop(mult);
            }
        }
    }
    public void applyChanges() {

        calculated = false;
        bias += dBias;
        dBias = 0;
        if (inputs != null) {
            for (int i = 0; i < inputs.size(); ++i) {
                inputs.get(i).applyChanges();
            }
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
    protected static double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }
    private static double dSigmoid(double x) {
        return 1 / (4*Math.pow(Math.cosh(x/2), 2));
    }
}