package com.jgaap.eventDrivers;

import com.jgaap.eventDrivers.*;
import com.jgaap.generics.*;

public class UniqueWordsCounterEventDriver extends EventDriver {

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
	public UniqueWordsCounterEventDriver() {
		wordCounter = new NaiveWordEventDriver();
	}
	
	/* ==================
	 * overriding methods
	 * ==================
	 */
	
	public String displayName() {
		return "Unique words count";
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
