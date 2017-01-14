package Brain;

import java.io.Serializable;

public class Axon implements Serializable {
	public Neuron attached;
	public Axon() {
		
	}
	public void emit(Signal s) {
		attached.receiveSignal(s);
	}
}
