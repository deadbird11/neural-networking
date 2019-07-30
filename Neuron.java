import java.util.ArrayList;

public class Neuron {
    private ArrayList<Synapse> inputs;
    private Double val;
    private double bias;

    public Neuron() {
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
        inputs = null;
        val = Double.valueOf(_val);
    }
    public double getBias() { return bias; }
    public void setBias(double instead) { bias = instead; }
    
    public double getVal() { return (val != null) ? val.doubleValue() : 0; }
    public void setVal(double instead) { val = instead; }
    public void updateVal() {
        if (val == null) {
            val = bias;
            for (int i = 0; i < inputs.size(); ++i) {
                val += inputs.get(i).getInput();
            }
            val = sigmoid(val);
        }
    }
    private static double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }
}