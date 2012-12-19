package com.jgaap.eventDrivers;

import com.jgaap.generics.*;

public class Writeprint_LetterCounterEventDriver2 extends EventDriver {

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
		EventSet chars = charDriver.createEventSet(text);
		double total = chars.size();
		for (int i=chars.size()-1; i>=0; i--) {
			Event e = chars.eventAt(i);
			if (!e.toString().matches("[A-Za-z]"))
				chars.removeEvent(e);
		}
		return Math.round(chars.size()/total);
	}
	
	public EventSet createEventSet(char[] text) throws EventGenerationException {
		
		EventSet res = new EventSet();
		res.addEvent(new Event(getValue(text)+"", this));
		//System.out.println(getValue(text));
		return res;
	}
}