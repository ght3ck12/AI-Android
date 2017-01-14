package Core;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.text.DefaultCaret;

import Brain.Brain;
import Brain.Hearing;
import Brain.Viewer;
import Vocabulary.FormEditor;
import Vocabulary.Generator;
import Vocabulary.Vocabulary;

public class Main extends JFrame implements ActionListener, WindowListener {
	private Brain b;
	private Hearing hearing;
	private Viewer viewer;
	private ArrayList <Vocabulary> vocabulary = new ArrayList <Vocabulary> ();
	private ArrayList <String> phrases = new ArrayList <String> ();
	private ArrayList <String> userInput = new ArrayList <String> ();
	private Display d;
	private JToolBar controls;
	private JTextField input;
	private JButton edit, makeDecision, forms, add, delete;
	private JComboBox <String> vocabularies;
	private JToggleButton phraseConstruction, thinkButton, memoriesButton, multipleButton, monitor;
	private JTextArea output;
	private boolean pc = false;
	private Generator g;
	private int counter = 0;
	private static int threshold = 14;
	private int current = 0;
	private boolean think = false;
	private boolean memories = false;
	private boolean multiple = false;
	public Main() {
		if (!load()) {
			b = new Brain();
		}
		if (vocabulary.size() == 0) {
			Vocabulary v = new Vocabulary();
			v.setName("Main");
			v.setVocabulary(phrases);
			vocabulary.add(v);
		}
		b.setRunning(true);
		hearing = new Hearing(b);
		hearing.start();
		viewer = new Viewer(b);
		viewer.start();
		g = new Generator(this, b);
		d = new Display(this);
		d.setVocabulary();
		addWindowListener(this);
		setTitle("Chat with AI Android");
		setBounds(0, 200, 1200, 400);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		controls = new JToolBar();
		controls.setLayout(new FlowLayout(FlowLayout.CENTER));
		controls.setPreferredSize(new Dimension(1200, 100));
		input = new JTextField(20);
		input.addActionListener(this);
		controls.add(input);
		thinkButton = new JToggleButton("Toggle Thinking");
		thinkButton.addActionListener(this);
		thinkButton.setSelected(think);
		controls.add(thinkButton);
		memoriesButton = new JToggleButton("Toggle Memories");
		memoriesButton.addActionListener(this);
		memoriesButton.setSelected(memories);
		controls.add(memoriesButton);
		multipleButton = new JToggleButton("Toggle Multiple Responses");
		multipleButton.addActionListener(this);
		multipleButton.setSelected(multiple);
		controls.add(multipleButton);
		monitor = new JToggleButton("Toggle monitor");
		monitor.addActionListener(this);
		monitor.setSelected(false);
		controls.add(monitor);
		edit = new JButton("Edit Base Vocabulary");
		edit.addActionListener(this);
		controls.add(edit);
		makeDecision = new JButton("Make Decision");
		makeDecision.addActionListener(this);
		controls.add(makeDecision);
		forms = new JButton("Show Forms");
		forms.addActionListener(this);
		controls.add(forms);
		add = new JButton("+");
		add.addActionListener(this);
		controls.add(add);
		delete = new JButton("-");
		delete.addActionListener(this);
		controls.add(delete);
		vocabularies = new JComboBox <String> (getNames());
		vocabularies.setSelectedIndex(0);
		vocabularies.addActionListener(this);
		controls.add(vocabularies);
		phraseConstruction = new JToggleButton("Phrase Construction");
		phraseConstruction.addActionListener(this);
		controls.add(phraseConstruction);
		add(controls, BorderLayout.NORTH);
		output = new JTextArea(20, 20);
		output.setEditable(false);
		output.setLineWrap(true);
		output.setWrapStyleWord(true);
		DefaultCaret caret = (DefaultCaret)output.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		add(new JScrollPane(output), BorderLayout.CENTER);
		output.setText("Welcome.  Press q to quit, a to add a phrase, and s to save.\n\n");
		setVisible(true);
	}
	public String findMemory() {
		for (int i = 0; i < userInput.size(); i++) {
			compute(i + ": " + userInput.get(i));
		}
		return "Remember when you said: " + userInput.get(c(userInput.size())) + " ?";
	}
	public void think(int seconds) {
		try {
			Thread.sleep(c(seconds)*1000);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}
	public String[] getNames() {
		String[] names = new String[vocabulary.size()];
		for (int i = 0; i < vocabulary.size(); i++) {
			names[i] = vocabulary.get(i).getName();
		}
		return names;
	}
	public String makeDecision(String q, ArrayList <String> possibilities) {
		if (possibilities.size() == 0) {
			return "Need a possibility";
		}
		q = "Make a decision: " + q;
		compute(q);
		for (int i = 0; i < possibilities.size(); i++) {
			String possibility = possibilities.get(i);
			compute("Think how much you want possibility " + String.valueOf(i) + ": " + possibility);
		}
		return possibilities.get(c(possibilities.size()));
	}
	public void setCurrent(int current) {
		this.current = current;
	}
	public int getCurrent() {
		return current;
	}
	public ArrayList <String> getBaseVocabulary() {
		return vocabulary.get(current).getVocabulary();
	}
	public Vocabulary getCurrentVocabulary() {
		return vocabulary.get(current);
	}
	public String getBaseVocabularyName() {
		return vocabulary.get(current).getName();
	}
	public void addBaseVocabulary(String name) {
		Vocabulary v = new Vocabulary(name);
		vocabulary.add(v);
		current = vocabulary.indexOf(v);
	}
	public void deleteBaseVocabulary() {
		if (current != -1) {
			vocabulary.remove(current);
		}
	}
	public void addBaseVocabularyEntry(String x) {
		vocabulary.get(current).getVocabulary().add(x);
	}
	public void deleteBaseVocabularyEntry(String x) {
		vocabulary.get(current).getVocabulary().remove(x);
	}
	public synchronized String process(String in) {
		if (in.equalsIgnoreCase("s")) {
			try {
				hearing.setRunning(false);
				viewer.setRunning(false);
				b.setRunning(false);
				Thread.sleep(3000);
				save();
				load();
				b.setRunning(true);
				hearing = new Hearing(b);
				hearing.start();
				viewer = new Viewer(b);
				viewer.start();
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
			return "Saved.";
		}
		String out = "";
		compute("Person said: " + in);
		if (think) {
			think(5);
		}
		if (multiple) {
			int amt = c(5);
			do {
				if (pc) {
					compute ("c0 for construct phrase");
					compute("c1 c2 or c3 for choose phrase from vocabulary");
					int choice = c(4);
					if (choice == 0) {
						out += g.construct() + "\n";
					} else {
						if (memories && userInput.size() > 0) {
							compute ("c0 for find memory");
							compute("c1 c2 or c3 for choose phrase from vocabulary");
							int decision = c(4);
							if (decision == 0) {
								out += findMemory() + "\n";
							} else {
								for (int i = 0; i < vocabulary.get(current).getVocabulary().size(); i++) {
									compute(i + ": " + vocabulary.get(current).getVocabulary().get(i));
								}
								out += vocabulary.get(current).getVocabulary().get(c(vocabulary.get(current).getVocabulary().size())) + "\n";
							}
						} else {
							for (int i = 0; i < vocabulary.get(current).getVocabulary().size(); i++) {
								compute(i + ": " + vocabulary.get(current).getVocabulary().get(i));
							}
							out += vocabulary.get(current).getVocabulary().get(c(vocabulary.get(current).getVocabulary().size())) + "\n";
						}
					}
				} else {
					if (memories && userInput.size() > 0) {
						compute ("c0 for find memory");
						compute("c1 c2 or c3 for choose phrase from vocabulary");
						int choice = c(4);
						if (choice == 0) {
							out += findMemory() + "\n";
						} else {
							for (int i = 0; i < vocabulary.get(current).getVocabulary().size(); i++) {
								compute(i + ": " + vocabulary.get(current).getVocabulary().get(i));
							}
							out += vocabulary.get(current).getVocabulary().get(c(vocabulary.get(current).getVocabulary().size())) + "\n";
						}
					} else {
						for (int i = 0; i < vocabulary.get(current).getVocabulary().size(); i++) {
							compute(i + ": " + vocabulary.get(current).getVocabulary().get(i));
						}
						out += vocabulary.get(current).getVocabulary().get(c(vocabulary.get(current).getVocabulary().size())) + "\n";
					}
				}
			} while (amt-- >= 0);
		} else {
			if (pc) {
				compute ("c0 for construct phrase");
				compute("c1 c2 or c3 for choose phrase from vocabulary");
				int choice = c(4);
				if (choice == 0) {
					out = g.construct();
				} else {
					if (memories && userInput.size() > 0) {
						compute ("c0 for find memory");
						compute("c1 c2 or c3 for choose phrase from vocabulary");
						int decision = c(4);
						if (decision == 0) {
							out = findMemory();
						} else {
							for (int i = 0; i < vocabulary.get(current).getVocabulary().size(); i++) {
								compute(i + ": " + vocabulary.get(current).getVocabulary().get(i));
							}
							out = vocabulary.get(current).getVocabulary().get(c(vocabulary.get(current).getVocabulary().size()));
						}
					} else {
						for (int i = 0; i < vocabulary.get(current).getVocabulary().size(); i++) {
							compute(i + ": " + vocabulary.get(current).getVocabulary().get(i));
						}
						out = vocabulary.get(current).getVocabulary().get(c(vocabulary.get(current).getVocabulary().size()));
					}
				}
			} else {
				if (memories && userInput.size() > 0) {
					compute ("c0 for find memory");
					compute("c1 c2 or c3 for choose phrase from vocabulary");
					int decision = c(4);
					if (decision == 0) {
						out = findMemory();
					} else {
						for (int i = 0; i < vocabulary.get(current).getVocabulary().size(); i++) {
							compute(i + ": " + vocabulary.get(current).getVocabulary().get(i));
						}
						out = vocabulary.get(current).getVocabulary().get(c(vocabulary.get(current).getVocabulary().size()));
					}
				} else {
					for (int i = 0; i < vocabulary.get(current).getVocabulary().size(); i++) {
						compute(i + ": " + vocabulary.get(current).getVocabulary().get(i));
					}
					out = vocabulary.get(current).getVocabulary().get(c(vocabulary.get(current).getVocabulary().size()));
				}
			}
		}
		compute("You said: " + out);
		return out;
	}
	public int c(int length) {
		//.274E-3
		if (counter == threshold) {
			counter = 0;
		}
		double a = b.getOutputs().get(counter);
		counter++;
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
	public int pick(ArrayList <String> phrases) {
		ArrayList <Double> analysis = new ArrayList <Double> ();
		for (int i = 0; i < phrases.size(); i++) {
			compute("Think how much you want: " + phrases.get(i));
			double value = 0.0;
			for (int j = 0; j < b.getOutputs().size(); j++) {
				value += b.getOutputs().get(j);
			}
			analysis.add(value);
		}
		return findMax(analysis);
	}
	public static int findMax(ArrayList <Double> inputs) {
		double max = -1.0;
		for (int i = 0; i < inputs.size(); i++) {
			if (inputs.get(i) > max) {
				max = inputs.get(i);
			}
		}
		for (int i = 0; i < inputs.size(); i++) {
			if (inputs.get(i) == max) {
				return i;
			}
		}
		return 0;
	}
	public void compute(String x) {
		ArrayList <String> stringDivided = divideString(x);
		for (int i = 0; i < stringDivided.size(); i++) {
			ArrayList <Double> inputs = toSize(processString(stringDivided.get(i)));
			b.activate(inputs);
		}
	}
	public ArrayList <String> divideString(String x) {
		int size = x.length();
		ArrayList <String> toReturn = new ArrayList <String> ();
		if (size < b.neuronMultiplier) {
			toReturn.add(x);
			return toReturn;
		}
		for (int i = 0; i < (int)Math.ceil(size/b.neuronMultiplier)+1; i++) {
			if (b.neuronMultiplier*(i+1) > size) {
				toReturn.add(x.substring(b.neuronMultiplier*(i), size));
			} else {
				toReturn.add(x.substring(b.neuronMultiplier*(i), b.neuronMultiplier*(i+1)));
			}
		}
		return toReturn;
	}
	public ArrayList <Double> processString(String word) {
		int[] integers = new int[word.length()];
		for (int i = 0; i < word.length(); i++) {
			integers[i] = (int)word.charAt(i);
		}
		ArrayList <Double> doubleValues = new ArrayList <Double> ();
		for (int i = 0; i < integers.length; i++) {
			doubleValues.add(integers[i]/127.0);
		}
		return doubleValues;
	}
	public ArrayList <Double> toSize(ArrayList <Double> initial) {
		ArrayList <Double> toSize = new ArrayList <Double> ();
		toSize.addAll(initial);
		for (int i = 0; i < b.neuronMultiplier - initial.size(); i++) {
			toSize.add(0.0);
		}
		return toSize;
	}
	public void save() {
		g.save();
		File f0 = new File("./brain.txt");
		File f1 = new File("./phrases.txt");
		File f2 = new File("./vocabulary.txt");
		try {
			FileOutputStream fos0 = new FileOutputStream(f0);
			FileOutputStream fos1 = new FileOutputStream(f1);
			FileOutputStream fos2 = new FileOutputStream(f2);
			ObjectOutputStream oos0 = new ObjectOutputStream(fos0);
			ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
			ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
			oos0.writeObject(b);
			oos1.writeObject(phrases);
			oos2.writeObject(vocabulary);
			oos0.close();
			oos1.close();
			oos2.close();
			fos0.close();
			fos1.close();
			fos2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public boolean load() {
		File f0 = new File("./brain.txt");
		File f1 = new File("./phrases.txt");
		File f2 = new File("./vocabulary.txt");
		if (!f0.exists()) {
			return false;
		}
		try {
			try {
				FileInputStream fis1 = new FileInputStream(f1);
				FileInputStream fis2 = new FileInputStream(f2);
				ObjectInputStream ois1 = new ObjectInputStream(fis1);
				ObjectInputStream ois2 = new ObjectInputStream(fis2);
				phrases = (ArrayList <String>) ois1.readObject();
				vocabulary = (ArrayList <Vocabulary>) ois2.readObject();
				ois1.close();
				fis1.close();
				ois2.close();
				fis2.close();
			} catch (FileNotFoundException e) {
				System.out.println("Making new objects.");
			} catch (EOFException e) {
				System.out.println("Making new objects.");
			}
			FileInputStream fis0 = new FileInputStream(f0);
			ObjectInputStream ois0 = new ObjectInputStream(fis0);
			b = (Brain)ois0.readObject();
			ois0.close();
			fis0.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return true;
	}
	public static void main(String args[]) {
		new Main();
	}
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosing(WindowEvent e) {
		hearing.setRunning(false);
		viewer.setRunning(false);
		b.setRunning(false);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		save();
	}
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == input) {
			if (!b.isRunning() && isVisible()) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}
			userInput.add(input.getText());
			String out = process(input.getText());
			output.setText(output.getText() + "Person said: " + input.getText() + "\n");
			if (output != null) {
				output.setText(output.getText() + "AI android responded: " + out + "\n\n");
			}
			input.setText("");
		} else if (e.getSource() == thinkButton) {
			think = thinkButton.isSelected();
		} else if (e.getSource() == multipleButton) {
			multiple = multipleButton.isSelected();
		} else if (e.getSource() == monitor) {
			hearing.setMonitor(monitor.isSelected());
		} else if (e.getSource() == memoriesButton) {
			memories = memoriesButton.isSelected();
		} else if (e.getSource() == edit) {
			d.setVisible(true);
			d.setVocabulary();
		}  else if (e.getSource() == makeDecision) {
			Decision d = new Decision(this);
			if (d.getQuestion() == null || d.getQuestion() == "" || d.getDecision() == null || d.getDecision() == "") {
				return;
			}
			output.setText(output.getText() + "Input: " + d.getQuestion() + " AI android decided: " + d.getDecision() + "\n\n");
		}  else if (e.getSource() == forms) {
			FormEditor fe = new FormEditor(g);
		} else if (e.getSource() == phraseConstruction) {
			pc = phraseConstruction.isSelected();
		} else if (e.getSource() == vocabularies) {
			setCurrent(vocabularies.getSelectedIndex());
			d.setVocabulary();
		} else if (e.getSource() == add) {
			String o = JOptionPane.showInputDialog(this, "Enter the name of the vocabulary:");
			if (o == null || o.equals("") || o.equals(" ")){
				return;
			} else {
				addBaseVocabulary(o);
				vocabularies.addItem(getBaseVocabularyName());
			}
		} else if (e.getSource() == delete) {
			String name = getBaseVocabularyName();
			deleteBaseVocabulary();
			vocabularies.removeItem(name);
		}
	}
}
class JP extends JPanel {
	BufferedImage image = null;
	BufferedImage display = null;
	public JP() {
		try {
			image = ImageIO.read(new File("./female_expressions.jpg"));
		} catch (IOException e) {
			
		}
		setImage(1, 0);
		setPreferredSize(new Dimension(getW(), getH()));
	}
	@Override
	public int getWidth() {
		return image.getWidth()-200;
	}
	@Override
	public int getHeight() {
		return image.getHeight()-30;
	}
	public int getW() {
		return display.getWidth();
	}
	public int getH() {
		return display.getHeight();
	}
	public void setImage(int x, int y) {
		//x: 0 - 5
		//y: 0 - 4
		display = crop(image, new Rectangle((getWidth()/6)*x, (getHeight()/5)*y, getWidth()/6, getHeight()/5));
	}
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(display, 0, 0, null);
		repaint();
	}
	public BufferedImage crop(BufferedImage src, Rectangle rect) {
      BufferedImage dest = resizeImage(src.getSubimage(rect.x, rect.y, rect.width, rect.height));
      return dest;
   }
	private static BufferedImage resizeImage(BufferedImage originalImage){
		int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
		BufferedImage resizedImage = new BufferedImage(originalImage.getWidth()*2, originalImage.getHeight()*2, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, originalImage.getWidth()*2, originalImage.getHeight()*2, null);
		g.dispose();
		return resizedImage;
	}
}

