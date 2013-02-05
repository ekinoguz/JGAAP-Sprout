package com.jgaap.eventDrivers;

import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.NumericEventDriver;
import com.jgaap.generics.NumericEventSet;

public class Writeprint_LetterCounterEventDriver2 extends NumericEventDriver {

	/* ======
	 * fields
	 * ======
	 */
	
	/**
	 * Event driver to be used for character count.
	 */
	private EventDriver charDriver;
	

	/* ============
	 * constructors
	 * ============
	 */
	
	/**
	 * Default sentence counter event driver constructor.
	 */
	public Writeprint_LetterCounterEventDriver2() {
		charDriver = new CharacterEventDriver();
	}
	
	/* ==================
	 * overriding methods
	 * ==================
	 */
	
	public String displayName() {
		return "_WP_Average characters per word";
	}

	public String tooltipText() {
		return "The total number of letters.";
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
		EventSet chars = charDriver.createEventSet(text);
		for (int i=chars.size()-1; i>=0; i--) {
			Event e = chars.eventAt(i);
			if (!e.toString().matches("[A-Za-z]"))
				chars.removeEvent(e);
		}
		return chars.size()/total;
	}
	
	@Override
	public NumericEventSet createEventSet(char[] text) throws EventGenerationException {
		
		NumericEventSet res = new NumericEventSet();
		res.addEvent(new Event(getValue(text)+"", this));
		//System.out.println(getValue(text));
		return res;
	}
}
