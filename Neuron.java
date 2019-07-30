import java.util.ArrayList;

public class Neuron {
    private ArrayList<Synapse> inputs;
    private double val;
    private double bias;

    public Neuron() {
        inputs = null;
        val = 0;
        bias = 0;
    }
    public Neuron(ArrayList<Synapse> _inputs, double _val, double _bias) {
        inputs = _inputs;
        val = _val;
        bias = _bias;
    }
    public Neuron(ArrayList<Synapse> _inputs, double _bias) {
        inputs = _inputs;
        bias = _bias;
        val = 0;
        updateVal();
    }
    public Neuron(double _val) {
        inputs = null;
        val = _val;
    }
    public double getBias() { return bias; }
    public void setBias(double instead) { bias = instead; }
    
    public double getVal() { return val; }
    public void setVal(double instead) { val = instead; }
    public void updateVal() {
        val = bias;
        for (Synapse s : inputs) {
            val += s.getInput();
        }
    }
}