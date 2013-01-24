package com.jgaap.eventDrivers;

import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.NumericEventDriver;
import com.jgaap.generics.NumericEventSet;

public class NF_SyllableCounterEventDriver extends NumericEventDriver {

	/* ======
	 * fields
	 * ======
	 */
	
	/**
	 * Event driver to be used for syllable count.
	 */
	private EventDriver syllablesDriver;
	
	
	/* ============
	 * constructors
	 * ============
	 */
	
	/**
	 * Default syllables counter event driver constructor.
	 */
	public NF_SyllableCounterEventDriver() {
		syllablesDriver = new WordSyllablesEventDriver();
	}
	
	/* ==================
	 * overriding methods
	 * ==================
	 */
	
	public String displayName() {
		return "9F_Average Syllables in Word";
	}

	public String tooltipText() {
		return "Average syllables in word";
	}

	public boolean showInGUI() {
		return true;
	}

	public double getValue(char[] text) throws EventGenerationException {
		EventDriver wordsDriver = new NaiveWordEventDriver();
		double total = 1;
		try{
			total = wordsDriver.createEventSet(text).size();
		} catch (Exception e) {
			System.out.println(e);
		}
		EventSet syllables = syllablesDriver.createEventSet(text);
		int i,sum = 0;
		for (i=0; i<syllables.size(); i++)
			sum += Integer.parseInt(syllables.eventAt(i).toString());
		return Math.round(sum/total); 
	}
	
	public NumericEventSet createEventSet(char[] text) throws EventGenerationException {
		
		NumericEventSet res = new NumericEventSet();
		res.addEvent(new Event(getValue(text)+"", this));
		//System.out.println(getValue(text));
		return res;
	}
}
