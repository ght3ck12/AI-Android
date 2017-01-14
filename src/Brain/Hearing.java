package Brain;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class Hearing extends Thread {
	private Brain b;
	private AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
    private TargetDataLine microphone;
    private SourceDataLine speakers;
    private ByteArrayOutputStream out = new ByteArrayOutputStream();
    private int numBytesRead;
    private int CHUNK_SIZE = 1024;
    private byte[] data;
    private int bytesRead = 0;
    private boolean running = true;
    private boolean monitor = false;
    public Hearing(Brain b) {
    	this.b = b;
    	try {
	    	microphone = AudioSystem.getTargetDataLine(format);
	        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
	        microphone = (TargetDataLine) AudioSystem.getLine(info);
	        microphone.open(format);
	        data = new byte[microphone.getBufferSize() / 5];
	        microphone.start();
	        DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
	        speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
	        speakers.open(format);
	        speakers.start();
    	} catch (LineUnavailableException e) {
    		e.printStackTrace();
    	}
    }
    
    public void run() {
    	while (running) {
    		int counter = 0;
    		do {
    			try {
    				Thread.sleep(2);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
    			numBytesRead = microphone.read(data, 0, CHUNK_SIZE);
        		out.write(data, 0, numBytesRead);
        		if (monitor) {
                	speakers.write(data, 0, numBytesRead);
                }
    			counter++;
    		} while (counter < 1000);
    		ArrayList <Double> inputs = new ArrayList <Double> ();
			for (int i = 0; i < out.toByteArray().length; i++) {
					inputs.add(convertByteToDouble(out.toByteArray()[i]));
			}
			
			for (int i = 0; i < Math.ceil((out.toByteArray().length) / b.neuronMultiplier); i++) {
				ArrayList <Double> in = new ArrayList <Double> ();
				if (i == Math.ceil((out.toByteArray().length) / b.neuronMultiplier) - 1) {
					in.addAll(inputs.subList(b.neuronMultiplier, inputs.size() - 1));
					for (int j = 0; j < (b.neuronMultiplier - ((inputs.size() - 1) - b.neuronMultiplier)); j++) {
						in.add(0.0);
					}
				} else {
					in.addAll(inputs.subList(b.neuronMultiplier, (i+1)*b.neuronMultiplier));
				}
				if (in.size() > 0) {
					b.activate(in);
				}
			}
			out.reset();
			try {
				Thread.sleep(5);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
    	}
    }
    public double convertByteToDouble(byte b) {
    	int i = b + 127;
    	return i/255.0;
    }
    public void setRunning(boolean running) {
		this.running = running;
	}
	public void setMonitor(boolean monitor) {
		this.monitor = monitor;
	}
}
