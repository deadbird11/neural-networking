public class Synapse {
    // defines connection between 2 neurons
    // input neuron
    private Neuron from;
    // possibly do not need this, deciding after back prop
    private Neuron to;
    // strength of connection, aka influence in deciding output
    private double weight;
    private double dWeight = 0;
    boolean backpropped = false;
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
    }
    public double getWeight() { return weight; }
    public void setWeight(double instead) { weight = instead; }

    public void setFrom(Neuron instead) { from = instead; }
    public void setTo(Neuron instead) { to = instead; }
    
    public double getInput() {
        // goes back recursively through network calculating the values
        from.updateVal();
        return from.getVal() * weight;
    }
    public void backprop(double mult) {
        if (!backpropped) {
            backpropped = true;
            dWeight += mult * from.getVal();
            from.innerBackprop(weight * mult);
        }
    }
    public void applyChanges() {
        backpropped = false;
        weight += dWeight;
        dWeight = 0;
    }
}