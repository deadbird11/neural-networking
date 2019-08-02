public class Synapse {
    // defines connection between 2 neurons
    // input neuron
    private Neuron from;
    // possibly do not need this, deciding after back prop
    private Neuron to;
    // strength of connection, aka influence in deciding output
    private double weight;
    private double dWeight = 0;
    private double backReturn = 0;


    public Synapse() {
        // maybe delete, doesn't really make sense
        weight = 0;
        from = null;
        to = null;
    }
    public Synapse(Neuron _from, Neuron _to, double _weight) {
        from = _from;
        to = _to;
        weight = _weight;
        from = _from;
        if (from != null) {
            from.addOutput(this);
        }
            
    }
    public double getWeight() { return weight; }
    public void setWeight(double instead) { weight = instead; }

    public void setFrom(Neuron instead) { from = instead; from.addOutput(this); }
    public void setTo(Neuron instead) { to = instead; to.addInput(this); }
    
    public double getOutput() {
        // goes back recursively through network calculating the values
        from.updateNetwork();
        return from.getVal() * weight;
    }
    public double backprop() {
        double nextBackprop = to.backprop();
        dWeight += nextBackprop * from.getVal();
        backReturn = weight * nextBackprop;
        return backReturn;
    }
    public void applyChanges() {
        weight -= dWeight;
        dWeight = 0;
        to.applyChanges();
    }
}