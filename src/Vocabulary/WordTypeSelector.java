package Vocabulary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

public class WordTypeSelector extends JComboBox<String> {
	public WordTypeSelector() {
		super();
		addItem("Select Type: ");
		addItem("PERIOD");
		addItem("COMMA");
		addItem("QUESTION_MARK");
		addItem("EXCLAMATION_MARK");
		addItem("ARTICLE");
		addItem("ADJECTIVE");
		addItem("ADVERB");
		addItem("CONJUNCTION");
		addItem("INTERJECTION");
		addItem("NOUN");
		addItem("SUBJECT_PRONOUN");
		addItem("OBJECT_PRONOUN");
		addItem("POSSESSIVE_PRONOUN");
		addItem("INDEFINITE_PRONOUN");
		addItem("RELATIVE_PRONOUN");
		addItem("INTENSIVE_PRONOUN");
		addItem("DEMONSTRATIVE_PRONOUN");
		addItem("INTERROGATIVE_PRONOUN");
		addItem("PREPOSITION");
		addItem("ACTION_VERB");
		addItem("LINKING_VERB");
		addItem("HELPING_VERB");
		addItem("UNKNOWN");
		setSelectedIndex(0);
	}
	public PartOfSpeech getType() {
		if (String.valueOf(getSelectedItem()).equals("PERIOD")) {
			return PartOfSpeech.PERIOD;
		} else if (String.valueOf(getSelectedItem()).equals("COMMA")) {
			return PartOfSpeech.COMMA;
		} else if (String.valueOf(getSelectedItem()).equals("QUESTION_MARK")) {
			return PartOfSpeech.QUESTION_MARK;
		} else if (String.valueOf(getSelectedItem()).equals("EXCLAMATION_MARK")) {
			return PartOfSpeech.EXCLAMATION_MARK;
		} else if (String.valueOf(getSelectedItem()).equals("ARTICLE")) {
			return PartOfSpeech.ARTICLE;
		} else if (String.valueOf(getSelectedItem()).equals("ADJECTIVE")) {
			return PartOfSpeech.ADJECTIVE;
		} else if (String.valueOf(getSelectedItem()).equals("ADVERB")) {
			return PartOfSpeech.ADVERB;
		} else if (String.valueOf(getSelectedItem()).equals("CONJUNCTION")) {
			return PartOfSpeech.CONJUNCTION;
		} else if (String.valueOf(getSelectedItem()).equals("INTERJECTION")) {
			return PartOfSpeech.INTERJECTION;
		} else if (String.valueOf(getSelectedItem()).equals("NOUN")) {
			return PartOfSpeech.NOUN;
		} else if (String.valueOf(getSelectedItem()).equals("SUBJECT_PRONOUN")) {
			return PartOfSpeech.SUBJECT_PRONOUN;
		} else if (String.valueOf(getSelectedItem()).equals("OBJECT_PRONOUN")) {
			return PartOfSpeech.OBJECT_PRONOUN;
		} else if (String.valueOf(getSelectedItem()).equals("POSSESSIVE_PRONOUN")) {
			return PartOfSpeech.POSSESSIVE_PRONOUN;
		} else if (String.valueOf(getSelectedItem()).equals("INDEFINITE_PRONOUN")) {
			return PartOfSpeech.INDEFINITE_PRONOUN;
		} else if (String.valueOf(getSelectedItem()).equals("RELATIVE_PRONOUN")) {
			return PartOfSpeech.RELATIVE_PRONOUN;
		} else if (String.valueOf(getSelectedItem()).equals("INTENSIVE_PRONOUN")) {
			return PartOfSpeech.INTENSIVE_PRONOUN;
		} else if (String.valueOf(getSelectedItem()).equals("DEMONSTRATIVE_PRONOUN")) {
			return PartOfSpeech.DEMONSTRATIVE_PRONOUN;
		} else if (String.valueOf(getSelectedItem()).equals("INTERROGATIVE_PRONOUN")) {
			return PartOfSpeech.INTERROGATIVE_PRONOUN;
		} else if (String.valueOf(getSelectedItem()).equals("PREPOSITION")) {
			return PartOfSpeech.PREPOSITION;
		} else if (String.valueOf(getSelectedItem()).equals("ACTION_VERB")) {
			return PartOfSpeech.ACTION_VERB;
		} else if (String.valueOf(getSelectedItem()).equals("LINKING_VERB")) {
			return PartOfSpeech.LINKING_VERB;
		} else if (String.valueOf(getSelectedItem()).equals("HELPING_VERB")) {
			return PartOfSpeech.HELPING_VERB;
		} else if (String.valueOf(getSelectedItem()).equals("UNKNOWN")) {
			return PartOfSpeech.UNKNOWN;
		} else {
			return PartOfSpeech.UNKNOWN;
		}
	}
	public void setType(PartOfSpeech t) {
		if (t == PartOfSpeech.PERIOD) {
			setSelectedIndex(1);
		} else if (t == PartOfSpeech.COMMA) {
			setSelectedIndex(2);
		} else if (t == PartOfSpeech.QUESTION_MARK) {
			setSelectedIndex(3);
		} else if (t == PartOfSpeech.EXCLAMATION_MARK) {
			setSelectedIndex(4);
		} else if (t == PartOfSpeech.ARTICLE) {
			setSelectedIndex(5);
		} else if (t == PartOfSpeech.ADJECTIVE) {
			setSelectedIndex(6);
		} else if (t == PartOfSpeech.ADVERB) {
			setSelectedIndex(7);
		} else if (t == PartOfSpeech.CONJUNCTION) {
			setSelectedIndex(8);
		} else if (t == PartOfSpeech.INTERJECTION) {
			setSelectedIndex(9);
		} else if (t == PartOfSpeech.NOUN) {
			setSelectedIndex(10);
		} else if (t == PartOfSpeech.SUBJECT_PRONOUN) {
			setSelectedIndex(11);
		} else if (t == PartOfSpeech.OBJECT_PRONOUN) {
			setSelectedIndex(12);
		} else if (t == PartOfSpeech.POSSESSIVE_PRONOUN) {
			setSelectedIndex(13);
		} else if (t == PartOfSpeech.INDEFINITE_PRONOUN) {
			setSelectedIndex(14);
		} else if (t == PartOfSpeech.RELATIVE_PRONOUN) {
			setSelectedIndex(15);
		} else if (t == PartOfSpeech.INTENSIVE_PRONOUN) {
			setSelectedIndex(16);
		} else if (t == PartOfSpeech.DEMONSTRATIVE_PRONOUN) {
			setSelectedIndex(17);
		} else if (t == PartOfSpeech.INTERROGATIVE_PRONOUN) {
			setSelectedIndex(18);
		} else if (t == PartOfSpeech.PREPOSITION) {
			setSelectedIndex(19);
		} else if (t == PartOfSpeech.ACTION_VERB) {
			setSelectedIndex(20);
		} else if (t == PartOfSpeech.LINKING_VERB) {
			setSelectedIndex(21);
		} else if (t == PartOfSpeech.HELPING_VERB) {
			setSelectedIndex(22);
		} else if (t == PartOfSpeech.UNKNOWN) {
			setSelectedIndex(23);
		}
	}
}
