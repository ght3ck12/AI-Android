package Brain;

import java.io.Serializable;

public class Dendrite implements Serializable {
	public Axon a;
	private double resistance = 0.0;
	public Dendrite() {
		
	}
	public void transmit(Signal s) {
		if (s.message == Message.KEEP_CHARGE) {
			resistance = resistance - .1;
		} else if (s.message == Message.PASS_CHARGE_ON) {
			resistance = resistance + .1;
		}
		s.charge = Math.sin(resistance + s.charge);
		a.emit(s);
	}
}
