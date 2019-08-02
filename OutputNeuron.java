public class OutputNeuron extends Neuron {

    private double expected;

    public void setExpected(double d) {
        expected = d;
    }

    @Override
    public double backprop() {
        dBias = dSigmoid() * 2 * (val - expected);
        return dBias;
    }
}