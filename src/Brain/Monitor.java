package Brain;

import java.io.Serializable;
import java.util.ArrayList;

public class Monitor implements Serializable {
	private static int MAX_SIGNALS = 10000;
	private ArrayList <Signal> signals = new ArrayList <Signal> ();
	public boolean maxSignalsReached = false;
	public boolean isAdding, isTerminating;
	public Monitor() {
		isAdding = false;
		isTerminating = false;
	}
	public synchronized void addSignal(Signal s) {
		isAdding = true;
		while (isTerminating) {
			try {
				wait();
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
		signals.add(s);
		if (signals.size() == MAX_SIGNALS) {
			maxSignalsReached = true;
		}
		notifyAll();
		isAdding = false;
	}
	public synchronized void terminateSignal(Signal s) {
		isTerminating = true;
		while (isAdding) {
			try {
				wait();
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
		signals.remove(s);
		maxSignalsReached = false;
		notifyAll();
		isTerminating = false;
	}
	public boolean containsSignal(Signal s) {
		return signals.contains(s);
	}
}
