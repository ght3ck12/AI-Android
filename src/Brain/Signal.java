package Brain;

import java.io.Serializable;

public class Signal implements Serializable {
	public double charge;
	public Message message;
	public Signal() {
		
	}
}

enum Message {
	PASS_CHARGE_ON, KEEP_CHARGE;
}