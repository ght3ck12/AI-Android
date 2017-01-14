package Vocabulary;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class WordBank implements Serializable {
	private ArrayList <PartOfSpeech> types;
	private ArrayList <String> words;
	private ArrayList <String> connections;
	private ArrayList <String> keys;
	public WordBank() {
		types = new ArrayList <PartOfSpeech> ();
		words = new ArrayList <String> ();
		connections = new ArrayList <String> ();
		keys = new ArrayList <String> ();
		types.add(PartOfSpeech.PERIOD);
		words.add(".");
		types.add(PartOfSpeech.COMMA);
		words.add(",");
		types.add(PartOfSpeech.EXCLAMATION_MARK);
		words.add("!");
		types.add(PartOfSpeech.QUESTION_MARK);
		words.add("?");
		types.add(PartOfSpeech.ARTICLE);
		words.add("a");
		types.add(PartOfSpeech.ARTICLE);
		words.add("an");
		types.add(PartOfSpeech.ARTICLE);
		words.add("the");
		types.add(PartOfSpeech.ARTICLE);
		words.add("some");
		types.add(PartOfSpeech.SUBJECT_PRONOUN);
		words.add("i");
		types.add(PartOfSpeech.SUBJECT_PRONOUN);
		words.add("it");
		types.add(PartOfSpeech.SUBJECT_PRONOUN);
		words.add("we");
		types.add(PartOfSpeech.SUBJECT_PRONOUN);
		words.add("you");
		types.add(PartOfSpeech.OBJECT_PRONOUN);
		words.add("me");
		types.add(PartOfSpeech.OBJECT_PRONOUN);
		words.add("it");
		types.add(PartOfSpeech.OBJECT_PRONOUN);
		words.add("you");
		types.add(PartOfSpeech.OBJECT_PRONOUN);
		words.add("them");
		types.add(PartOfSpeech.OBJECT_PRONOUN);
		words.add("us");
		types.add(PartOfSpeech.POSSESSIVE_PRONOUN);
		words.add("my");
		types.add(PartOfSpeech.POSSESSIVE_PRONOUN);
		words.add("your");
		types.add(PartOfSpeech.POSSESSIVE_PRONOUN);
		words.add("our");
		types.add(PartOfSpeech.POSSESSIVE_PRONOUN);
		words.add("their");
		types.add(PartOfSpeech.INDEFINITE_PRONOUN);
		words.add("few");
		types.add(PartOfSpeech.INDEFINITE_PRONOUN);
		words.add("everyone");
		types.add(PartOfSpeech.INDEFINITE_PRONOUN);
		words.add("all");
		types.add(PartOfSpeech.INDEFINITE_PRONOUN);
		words.add("some");
		types.add(PartOfSpeech.INDEFINITE_PRONOUN);
		words.add("anything");
		types.add(PartOfSpeech.INDEFINITE_PRONOUN);
		words.add("nobody");
		types.add(PartOfSpeech.RELATIVE_PRONOUN);
		words.add("who");
		types.add(PartOfSpeech.RELATIVE_PRONOUN);
		words.add("whom");
		types.add(PartOfSpeech.RELATIVE_PRONOUN);
		words.add("which");
		types.add(PartOfSpeech.RELATIVE_PRONOUN);
		words.add("whoever");
		types.add(PartOfSpeech.RELATIVE_PRONOUN);
		words.add("whomever");
		types.add(PartOfSpeech.RELATIVE_PRONOUN);
		words.add("whichever");
		types.add(PartOfSpeech.RELATIVE_PRONOUN);
		words.add("that");
		types.add(PartOfSpeech.INTENSIVE_PRONOUN);
		words.add("myself");
		types.add(PartOfSpeech.INTENSIVE_PRONOUN);
		words.add("himself");
		types.add(PartOfSpeech.INTENSIVE_PRONOUN);
		words.add("herself");
		types.add(PartOfSpeech.INTENSIVE_PRONOUN);
		words.add("themselves");
		types.add(PartOfSpeech.INTENSIVE_PRONOUN);
		words.add("itself");
		types.add(PartOfSpeech.INTENSIVE_PRONOUN);
		words.add("yourself");
		types.add(PartOfSpeech.INTENSIVE_PRONOUN);
		words.add("yourselves");
		types.add(PartOfSpeech.INTENSIVE_PRONOUN);
		words.add("ourselves");
		types.add(PartOfSpeech.DEMONSTRATIVE_PRONOUN);
		words.add("these");
		types.add(PartOfSpeech.DEMONSTRATIVE_PRONOUN);
		words.add("those");
		types.add(PartOfSpeech.DEMONSTRATIVE_PRONOUN);
		words.add("this");
		types.add(PartOfSpeech.DEMONSTRATIVE_PRONOUN);
		words.add("that");
		types.add(PartOfSpeech.DEMONSTRATIVE_PRONOUN);
		words.add("such");
		types.add(PartOfSpeech.INTERROGATIVE_PRONOUN);
		words.add("who");
		types.add(PartOfSpeech.INTERROGATIVE_PRONOUN);
		words.add("whom");
		types.add(PartOfSpeech.INTERROGATIVE_PRONOUN);
		words.add("which");
		types.add(PartOfSpeech.INTERROGATIVE_PRONOUN);
		words.add("what");
		types.add(PartOfSpeech.INTERROGATIVE_PRONOUN);
		words.add("whoever");
		types.add(PartOfSpeech.INTERROGATIVE_PRONOUN);
		words.add("whomever");
		types.add(PartOfSpeech.INTERROGATIVE_PRONOUN);
		words.add("whichever");
		types.add(PartOfSpeech.INTERROGATIVE_PRONOUN);
		words.add("whatever");
	}
	public ArrayList <String> getWords(PartOfSpeech t, Form f) {
		ArrayList <String> w = new ArrayList <String> ();
		for (int i = 0; i < words.size(); i++) {
			if (t == types.get(i)) {
				w.add(words.get(i));
			}
		}
		for (int i = 0; i < connections.size(); i++) {
			PartOfSpeech one = getTypeFromWord(getKeyFromIndex(i));
			PartOfSpeech two = getTypeFromWord(getConnectionFromIndex(i));
			if (!f.contains(one, two)) {
				w.remove(keys.get(i));
				w.remove(connections.get(i));
			}
		}
		return w;
	}
	public String getWord(int index) {
		return words.get(index);
	}
	public PartOfSpeech getType(int index) {
		return types.get(index);
	}
	public void addWord(PartOfSpeech t, String word) {
		types.add(t);
		words.add(word);
	}
	public void deleteWord(int index) {
		types.remove(index);
		words.remove(index);
	}
	public void setType(int index, PartOfSpeech t, String word) {
		types.set(index, t);
		words.set(index, word);
	}
	public PartOfSpeech recognize(String x) {
		if (words.contains(x)) {
			return types.get(words.indexOf(x));
		} else {
			return PartOfSpeech.UNKNOWN;
		}
	}
	public int size() {
		return words.size();
	}
	public void addConnection(String key, String value) {
		connections.add(value);
		keys.add(key);
	}
	public String getConnectionFromKey(String key) {
		return connections.get(keys.indexOf(key));
	}
	public String getConnectionFromIndex(int index) {
		return connections.get(index);
	}
	public String getKeyFromIndex(int index) {
		return keys.get(index);
	}
	public void setConnectionAtIndex(int index, String value) {
		connections.set(index, value);
	}
	public void setKeyAtIndex(int index, String key) {
		keys.set(index, key);
	}
	public void deleteConnectionFromKey(String key) {
		connections.remove(keys.indexOf(key));
		keys.remove(key);
	}
	public void deleteConnectionFromIndex(int index) {
		connections.remove(index);
		keys.remove(index);
	}
	public boolean connectionsContainsKey(String key) {
		return keys.contains(key);
	}
	public int getConnectionsSize() {
		return connections.size();
	}
	public PartOfSpeech getTypeFromWord(String word) {
		return types.get(words.indexOf(word));
	}
}
