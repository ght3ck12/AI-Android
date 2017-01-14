package Brain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Brain implements Serializable {
	private Monitor m;
	public int neuronMultiplier = 15;
	private Neuron[][] neurons;
	private boolean running = false;
	public Brain() {
		m = new Monitor();
		neurons = new Neuron[neuronMultiplier][neuronMultiplier];
		addNeurons();
		growConnections();
	}
	public void addNeurons() {
		for (int i = 0; i < neuronMultiplier; i++) {
			for (int j = 0; j < neuronMultiplier; j++) {
				neurons[i][j] = new Neuron(m);
			}
		}
	}
	public void growConnections() {
		for (int i = 0; i < neuronMultiplier; i++) {
			for (int j = 0; j < neuronMultiplier; j++) {
				for (int k = 0; k < neuronMultiplier; k++) {
					for (int l = 0; l < neuronMultiplier; l++) {
						if (i != k && j != l) {
							connect(neurons[i][j], neurons[k][l]);
						}
					}
				}
			}
		}
	}
	public void connect(Neuron n0, Neuron n1) {
		Dendrite d0 = new Dendrite();
		Dendrite d1 = new Dendrite();
		Axon a0 = new Axon();
		Axon a1 = new Axon();
		n0.addDendrite(d0);
		d0.a = a0;
		a0.attached = n1;
		n1.addDendrite(d1);
		d1.a = a1;
		a1.attached = n1;
	}
	public void activate(ArrayList <Double> inputs) {
		Signal[] signals = new Signal[neuronMultiplier];
		for (int i = 0; i < signals.length; i++) {
			signals[i] = new InputSignal();
			signals[i].message = Message.KEEP_CHARGE;
			signals[i].charge = inputs.get(i);
			neurons[0][i].receiveSignal(signals[i]);
		}
	}
	public ArrayList <Double> getOutputs() {
		ArrayList <Double> outputs = new ArrayList <Double> ();
		for (int i = 0; i < neuronMultiplier; i++) {
			outputs.add(neurons[neuronMultiplier-1][i].nucleus);
		}
		return outputs;
	}
	public boolean isRunning() {
		return running;
	}
	public void setRunning(boolean running) {
		this.running = running;
		if (running) {
			for (int i = 0; i < neuronMultiplier; i++) {
				for (int j = 0; j < neuronMultiplier; j++) {
					neurons[i][j].setRunning(true);
					neurons[i][j].start();
				}
			}
		} else {
			for (int i = 0; i < neuronMultiplier; i++) {
				for (int j = 0; j < neuronMultiplier; j++) {
					neurons[i][j].setRunning(false);
				}
			}
		}
	}
}
