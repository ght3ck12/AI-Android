package Vocabulary;

import java.io.Serializable;
import java.util.ArrayList;

public class Vocabulary implements Serializable {
	private String name;
	private ArrayList <String> vocabulary;
	public Vocabulary() {
		vocabulary = new ArrayList <String> ();
	}
	public Vocabulary(String name) {
		this();
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList <String> getVocabulary() {
		return vocabulary;
	}
	public void setVocabulary(ArrayList <String> vocabulary) {
		this.vocabulary = vocabulary;
	}
}
