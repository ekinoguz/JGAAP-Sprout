package com.jgaap.eventDrivers;

import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;

public class NF_UniqueWordsCounterEventDriver1 extends EventDriver {

	/* ======
	 * fields
	 * ======
	 */
	
	/**
	 * Event driver to be used for word count.
	 */
	private EventDriver wordCounter;
	
	
	/* ============
	 * constructors
	 * ============
	 */
	
	/**
	 * Default unique words counter event driver constructor.
	 */
	public NF_UniqueWordsCounterEventDriver1() {
		wordCounter = new NaiveWordEventDriver();
	}
	
	/* ==================
	 * overriding methods
	 * ==================
	 */
	
	public String displayName() {
		return "9F_Number of unique words in the document";
	}

	public String tooltipText() {
		return "The total number of unique words";
	}

	public boolean showInGUI() {
		return true;
	}

	public double getValue(char[] text) throws EventGenerationException {
		EventSet words = wordCounter.createEventSet(text);		
		return (new EventHistogram(words)).getNTypes();
	}
	
	@Override
	public EventSet createEventSet(char[] text) throws EventGenerationException {
		EventSet res = new EventSet();
		res.addEvent(new Event(getValue(text)+"", this));
		//System.out.println(getValue(text));
		return res;
	}
}
