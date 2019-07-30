import java.util.ArrayList;

public class Neuron {
    private ArrayList<Synapse> inputs;
    private double val;

    public Neuron() {
        inputs = null;
        val = 0;
    }
    public Neuron(ArrayList<Synapse> _inputs, double _val) {
        inputs = _inputs;
        val = _val;
    }
    public Neuron(double _val) {
        inputs = null;
        val = _val;
    }

    public double getVal() { return val; }
    public void setVal(double instead) { val = instead; }
}