package Vocabulary;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Form implements Serializable {
	private ArrayList <PartOfSpeech> types;
	private int length;
	public Form() {
		types = new ArrayList <PartOfSpeech> ();
	}
	public void addType(PartOfSpeech x) {
		types.add(x);
	}
	public void setType(int index, PartOfSpeech type) {
		types.set(index, type);
	}
	public PartOfSpeech getType (int index) {
		return types.get(index);
	}
	public int size() {
		return types.size();
	}
	public boolean contains(PartOfSpeech one, PartOfSpeech two) {
		if (types.contains(one) && types.contains(two)) {
			return true;
		}
		return false;
	}
	public String getTypeString(PartOfSpeech t) {
		if (t == PartOfSpeech.PERIOD) {
			return "PERIOD";
		} else if (t == PartOfSpeech.COMMA) {
			return "COMMA";
		} else if (t == PartOfSpeech.QUESTION_MARK) {
			return "QUESTION MARK";
		}else if (t == PartOfSpeech.EXCLAMATION_MARK) {
			return "EXCLAMATION_MARK";
		}else if (t == PartOfSpeech.ARTICLE) {
			return "ARTICLE";
		}else if (t == PartOfSpeech.ADJECTIVE) {
			return "ADJECTIVE";
		}else if (t == PartOfSpeech.ADVERB) {
			return "ADVERB";
		}else if (t == PartOfSpeech.CONJUNCTION) {
			return "CONJUNCTION";
		}else if (t == PartOfSpeech.INTERJECTION) {
			return "INTERJECTION";
		}else if (t == PartOfSpeech.NOUN) {
			return "NOUN";
		}else if (t == PartOfSpeech.SUBJECT_PRONOUN) {
			return "SUBJECT_PRONOUN";
		}else if (t == PartOfSpeech.OBJECT_PRONOUN) {
			return "OBJECT_PRONOUN";
		}else if (t == PartOfSpeech.POSSESSIVE_PRONOUN) {
			return "POSSESSIVE_PRONOUN";
		}else if (t == PartOfSpeech.INDEFINITE_PRONOUN) {
			return "INDEFINITE_PRONOUN";
		}else if (t == PartOfSpeech.RELATIVE_PRONOUN) {
			return "RELATIVE_PRONOUN";
		}else if (t == PartOfSpeech.INTENSIVE_PRONOUN) {
			return "INTENSIVE_PRONOUN";
		}else if (t == PartOfSpeech.DEMONSTRATIVE_PRONOUN) {
			return "DEMONSTRATIVE_PRONOUN";
		}else if (t == PartOfSpeech.INTERROGATIVE_PRONOUN) {
			return "INTERROGATIVE_PRONOUN";
		}else if (t == PartOfSpeech.PREPOSITION) {
			return "PREPOSITION";
		}else if (t == PartOfSpeech.ACTION_VERB) {
			return "ACTION_VERB";
		}else if (t == PartOfSpeech.LINKING_VERB) {
			return "LINKING_VERB";
		}else if (t == PartOfSpeech.HELPING_VERB) {
			return "HELPING_VERB";
		}else if (t == PartOfSpeech.UNKNOWN) {
			return "UNKNOWN";
		} else {
			return "UNKNOWN";
		}
	}
	public String getFormString() {
		String x = "";
		for (int i = 0; i < types.size(); i++) {
			x += getTypeString(types.get(i)) + " ";
		}
		return x;
	}
}
