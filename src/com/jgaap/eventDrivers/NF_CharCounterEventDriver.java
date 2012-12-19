package com.jgaap.eventDrivers;

import com.jgaap.generics.Event;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.generics.NumericEventDriver;
import com.jgaap.generics.NumericEventSet;

public class NF_CharCounterEventDriver extends NumericEventDriver {

	/* ======
	 * fields
	 * ======
	 */
	
	/**
	 * Event driver to be used for character count.
	 */
	private CharacterEventDriver charDriver;
	
	
	/* ============
	 * constructors
	 * ============
	 */
	
	/**
	 * Default sentence counter event driver constructor.
	 */
	public NF_CharCounterEventDriver() {
		charDriver = new CharacterEventDriver();
	}
	
	/* ==================
	 * overriding methods
	 * ==================
	 */
	
	public String displayName() {
		return "9F_Character count";
	}

	public String tooltipText() {
		return "The total number of characters.";
	}

	public boolean showInGUI() {
		return true;
	}

	public double getValue(char[] text) {
		return new String(text).length();
	}
	
	public NumericEventSet createEventSet(char[] text) throws EventGenerationException {
		
		NumericEventSet res = new NumericEventSet();
		res.addEvent(new Event(getValue(text)+"", this));
		//System.out.println(getValue(text));
		return res;
	}
}
