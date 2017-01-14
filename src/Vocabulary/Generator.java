package Vocabulary;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Brain.Brain;
import Core.Main;

public class Generator {
	private ArrayList <Form> forms;
	private Main m;
	private Brain b;
	private WordBank wb;
	public Generator(Main m, Brain b) {
		this.b = b;
		this.m = m;
		if (!load()) {
			forms = new ArrayList <Form> ();
			wb = new WordBank();
		}
	}
	public int pick(ArrayList <String> phrases) {
		for (int i = 0; i < phrases.size(); i++) {
			m.compute("Think how much you want to say " + phrases.get(i));
		}
		return m.c(phrases.size());
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
	public int parse(String x) { 
		Form f = new Form();
		String[] xSplit = x.split(" ");
		for (int i = 0; i < xSplit.length; i++) {
			PartOfSpeech t = wb.recognize(trim(xSplit[i]));
			f.addType(t);
			if (xSplit[i].contains(".")) {
				f.addType(PartOfSpeech.PERIOD);
			} else if (xSplit[i].contains(",")) {
				f.addType(PartOfSpeech.COMMA);
			} else if (xSplit[i].contains("?")) {
				f.addType(PartOfSpeech.QUESTION_MARK);
			} else if (xSplit[i].contains("!")) {
				f.addType(PartOfSpeech.EXCLAMATION_MARK);
			}
		}
		forms.add(f);
		return forms.indexOf(f);
	}
	public String construct() {
		ArrayList <String> formPhrases = new ArrayList <String> ();
		for (int i = 0; i < forms.size(); i++) {
			formPhrases.add(forms.get(i).getFormString());
		}
		Form f = forms.get(pick(formPhrases));
		String x = "";
		ArrayList <Boolean> connectionsMade = new ArrayList <Boolean> ();
		ArrayList <String> choices = new ArrayList <String> ();
		for (int i = 0; i < f.size(); i++) {
			if (wb.getWords(f.getType(i), f).size() != 0) {
				choices.add(wb.getWords(f.getType(i), f).get(pick(wb.getWords(f.getType(i), f))));
			} else {
				choices.add("");
			}
			if (wb.connectionsContainsKey(choices.get(i))) {
				connectionsMade.add(true);
			} else {
				connectionsMade.add(false);
			}
		}
		for (int i = 0; i < f.size(); i++) {
			if (connectionsMade.get(i) == true) {
				if (wb.connectionsContainsKey(choices.get(i))) {
					PartOfSpeech t = wb.getTypeFromWord(wb.getConnectionFromKey(choices.get(i)));
					for (int j = i; j < f.size(); j++) {
						if (connectionsMade.get(j) == false && t == wb.getTypeFromWord(choices.get(j))) {
							choices.set(j, wb.getConnectionFromKey(choices.get(i)));
							break;
						}
					}
				}	
			}
		}
		for (int i = 0; i < choices.size(); i++) {
			x = x + choices.get(i) + " ";
		}
		x = x.trim();
		return x;
	}
	public void addConnection(String c) {
		String[] cSplit = c.split(" ");
		if (cSplit.length == 2) {
			wb.addConnection(cSplit[0], cSplit[1]);
		}
	}
	public String trim(String x) {
		return x.replaceAll("[^\\w\\s]","").toLowerCase().trim();
	}
	public void set(int formNumber, int pos, PartOfSpeech t) {
		forms.get(formNumber).setType(pos, t);
	}
	public Form get(int formIndex) {
		return forms.get(formIndex);
	}
	public void delete(int formIndex) {
		forms.remove(formIndex);
	}
	public void add(String word) {
		wb.addWord(PartOfSpeech.UNKNOWN, word);
	}
	public void deleteWord(int index) {
		wb.deleteWord(index);
	}
	public void setWord(int index, PartOfSpeech t, String word) {
		wb.setType(index, t, word);
	}
	public String getWord(int index) {
		return wb.getWord(index);
	}
	public PartOfSpeech getType(int index) {
		return wb.getType(index);
	}
	public int wordbanksize() {
		return wb.size();
	}
	public int formssize() {
		return forms.size();
	}
	public WordBank getWordBank() {
		return wb;
	}
	public void save() {
		File f = new File("./wordbank.txt");
		File f0 = new File("./forms.txt");
		try {
			FileOutputStream fos = new FileOutputStream(f);
			FileOutputStream fos0 = new FileOutputStream(f0);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			ObjectOutputStream oos0 = new ObjectOutputStream(fos0);
			oos.writeObject(wb);
			oos0.writeObject(forms);
			oos.close();
			oos0.close();
			fos.close();
			fos0.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public boolean load() {
		File f = new File("./wordbank.txt");
		File f0 = new File("./forms.txt");
		if (!f.exists() || !f0.exists()) {
			return false;
		}
		try {
			FileInputStream fis = new FileInputStream(f);
			FileInputStream fis0 = new FileInputStream(f0);
			ObjectInputStream ois = new ObjectInputStream(fis);
			ObjectInputStream ois0 = new ObjectInputStream(fis0);
			wb = (WordBank)ois.readObject();
			forms = (ArrayList <Form>)ois0.readObject();
			ois.close();
			ois0.close();
			fis.close();
			fis0.close();
			return true;
		} catch (IOException e) {
			return false;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
}