package Brain;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Viewer extends Thread {
	private Brain b;
	private boolean running = true;
	public Viewer(Brain b) {
		this.b = b;
		setRunning(true);
	}
	public void run() {
		while (running) {
			try {
				Thread.sleep((long)Math.round((b.getOutputs().get(5) + 1.0) * 30));
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
		try {
			BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			ArrayList <Double> inputs = new ArrayList <Double> ();
			for (int i = 0; i < image.getWidth(); i++) {
				for (int j = 0; j < image.getHeight(); j++) {
					inputs.add(convertColorToDouble(new Color(image.getRGB(i, j)).getRed()));
					inputs.add(convertColorToDouble(new Color(image.getRGB(i, j)).getGreen()));
					inputs.add(convertColorToDouble(new Color(image.getRGB(i, j)).getBlue()));
				}
			}
			for (int i = 0; i < Math.ceil((image.getWidth()*image.getHeight()*3) / b.neuronMultiplier); i++) {
				ArrayList <Double> in = new ArrayList <Double> ();
				if (i == Math.ceil((image.getWidth() * image.getHeight()*3) / b.neuronMultiplier) - 1) {
					in.addAll(inputs.subList(i*b.neuronMultiplier, inputs.size() - 1));
					for (int j = 0; j < (b.neuronMultiplier - ((inputs.size() - 1) - i*b.neuronMultiplier)); j++) {
						in.add(0.0);
					}
				} else {
					in.addAll(inputs.subList(i*b.neuronMultiplier, (i+1)*b.neuronMultiplier));
				}
				b.activate(in);
			}
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	public double convertColorToDouble(int color) {
		return color/255.0;
	}
	public void setRunning(boolean running) {
		this.running = running;
	}
}
