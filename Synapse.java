public class Synapse {
    private Neuron from;
    private Neuron to;
    private double weight;
    private double bias;

    public Synapse() {
        weight = 0;
        bias = 0;
        from = null;
        to = null;
    }
    public Synapse(Neuron _from, Neuron _to, double _weight, double _bias) {
        from = _from;
        to = _to;
        weight = _weight;
        bias = _bias;
    }
    public double getWeight() { return weight; }
    public void setWeight(double instead) { weight = instead; }

    public double getBias() { return bias; }
    public void setBias(double instead) { bias = instead; }

    public void setFrom(Neuron instead) { from = instead; }
    public void setTo(Neuron instead) { to = instead; }
}