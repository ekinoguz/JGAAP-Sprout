package com.jgaap.eventDrivers;

import com.jgaap.eventDrivers.WordSyllablesEventDriver;
import com.jgaap.generics.*;

public class SyllableCounterEventDriver extends EventDriver {

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
	public SyllableCounterEventDriver() {
		syllablesDriver = new WordSyllablesEventDriver();
	}
	
	/* ==================
	 * overriding methods
	 * ==================
	 */
	
	public String displayName() {
		return "Syllables count";
	}

	public String tooltipText() {
		return "The total number of syllables";
	}

	public boolean showInGUI() {
		return true;
	}

	public double getValue(char[] text) throws EventGenerationException {
		EventSet syllables = syllablesDriver.createEventSet(text);
		int i,sum = 0;
		for (i=0; i<syllables.size(); i++)
			sum += Integer.parseInt(syllables.eventAt(i).toString());
		return sum; 
	}
	
	public EventSet createEventSet(char[] text) throws EventGenerationException {
		EventSet res = new EventSet();
		res.addEvent(new Event(getValue(text)+"", this));
		//System.out.println(getValue(text));
		return res;
	}
}
