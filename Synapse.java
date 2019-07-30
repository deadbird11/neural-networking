public class Synapse {
    private Neuron from;
    private Neuron to;
    private double weight;

    public Synapse() {
        weight = 0;
        from = null;
        to = null;
    }
    public Synapse(Neuron _from, Neuron _to, double _weight) {
        from = _from;
        to = _to;
        weight = _weight;
    }
    public double getWeight() { return weight; }
    public void setWeight(double instead) { weight = instead; }

    public void setFrom(Neuron instead) { from = instead; }
    public void setTo(Neuron instead) { to = instead; }
    
    public double getInput() {
        return from.getVal() * weight;
    }
}