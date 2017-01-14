package Brain;

import java.io.Serializable;

public class Neuron extends Thread implements Serializable {
	private Monitor m;
	private boolean running = false;
	public double nucleus;
	private int x, y;
	private double threshold;
	private Dendrite[] dendrites = new Dendrite[0];
	private double[] charges;
	private boolean available = true;
	public Neuron(Monitor m) {
		this.m = m;
		nucleus = randomize(-1.0, 1.0);
		threshold = randomize(-1.0, 1.0);
		charges = new double[0];
	}
	public void addDendrite(Dendrite d) {
		dendrites = incrementDendrites(dendrites);
		dendrites[dendrites.length - 1] = d;
	}
	public void run() {
		while (running) {
			try {
				Thread.sleep((long)Math.round((a() + 1.0)*500.0));
				if (i()) {
					releaseSignal(chooseDendrite());	
				}
			} catch (InterruptedException ie) {
				
			}
		}
	}
	public void setRunning(boolean running) {
		this.running = running;
	}
	public boolean i() {
		return sumOfCharges() >= threshold;
	}
	public void receiveSignal(Signal s) {
		switch (s.message) {
		case KEEP_CHARGE:
			if (m.containsSignal(s)) {
				m.terminateSignal(s);
			}
			if (charges.length == dendrites.length) {
				charges = new double[0];
			}
			charges = incrementArray(charges);
			charges[charges.length - 1] = s.charge;
			break;
		case PASS_CHARGE_ON:
			s.message = decideMessage();
			releaseSignal(s, chooseDendrite());
			break;
			default:
				break;
		}
		
	}
	public Dendrite chooseDendrite() {
		int c = c(dendrites.length);
		return dendrites[c];
	}
	public double sumOfCharges() {
		double sum = 0.0;
		int x = charges.length;
		for (int i = 0; i < x; i++) {
			sum += charges[i];
		}
		return Math.sin(sum);
	}
	public synchronized void releaseSignal(Dendrite d) {
		if (!m.maxSignalsReached) {
			Signal s = new Signal();
			s.charge = sumOfCharges();
			charges = new double[0];
			s.message = decideMessage();
			m.addSignal(s);
			d.transmit(s);
		}
	}
	public void releaseSignal(Signal s, Dendrite d) {
		d.transmit(s);
	}
	public Message decideMessage() {
		int c = c(2);
		switch(c) {
		case 0:
			return Message.KEEP_CHARGE;
		case 1:
			return Message.PASS_CHARGE_ON;
		default:
			return Message.KEEP_CHARGE;
		}
	}
	public int c(int length) {
		//.274E-3
		double a = a();
		String a0 = String.valueOf(a);
		if (a0.contains("E")) {
			a0 = a0.replaceAll("E-", "");
		}
		//.2743
		String l0 = String.valueOf(length);
		//3 (str)
		int l = l0.length();
		//1
		int power = l;
		//1
		int outOf = (int)Math.pow(10.0, power);
		//10
		int selection = Integer.parseInt(a0.substring(a0.length() - l - 1, a0.length() - 1));
		//3
		return (int)Math.floor(((double)selection/(double)outOf)*(double)length);
		//3/10
	}
	public synchronized double a() {
		String z = "";
		if (String.valueOf(nucleus).length() >= 10) {
			z = String.valueOf(this.nucleus).substring(0, 10);
		}
		if (z.contains("E")) {
			z = z.replaceAll("E-", "");
		}
		if (z.length() <= 4) {
			z += "12345";
		}
		String y = z.substring(z.length() - 5);
		y = "." + y;
		if (String.valueOf(y).length() >= 10) {
			y = String.valueOf(y).substring(0, 10);
		}
		double a = Double.parseDouble(y);
		a = a/7;
		nucleus = Math.sin(a);
		return Math.sin(a);
	}
	public double randomize(double min, double max) {
		return (Math.random() * (max - min)) + min;
	}
	public static double[] incrementArray(double[] smaller) {
		double[] larger = new double[smaller.length + 1];
		for (int i = 0; i < smaller.length; i++) {
			larger[i] = smaller[i];
		}
		return larger;
	}
	public static Dendrite[] incrementDendrites(Dendrite[] smaller) {
		Dendrite[] larger = new Dendrite[smaller.length + 1];
		for (int i = 0; i < smaller.length; i++) {
			larger[i] = smaller[i];
		}
		return larger;
	}
}
