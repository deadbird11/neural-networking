import java.util.ArrayList;

public class Neuron {
    // synapses connected on left
    private ArrayList<Synapse> inputs;
    // sigmoid value
    private Double val;
    private double bias;

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
        // for first layer, no inputs, bias is 0
        // might delete this, every neuron should have inputs
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
            val = bias;
            for (int i = 0; i < inputs.size(); ++i) {
                val += inputs.get(i).getInput();
            }
            val = sigmoid(val);
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
}