package com.jgaap.eventDrivers;

import com.jgaap.eventDrivers.*;
import com.jgaap.generics.*;

public class SentenceCounterEventDriver extends EventDriver {

	/* ======
	 * fields
	 * ======
	 */
	
	/**
	 * Event driver to be used for word count.
	 */
	private EventDriver sentencesDriver;
	
	
	/* ============
	 * constructors
	 * ============
	 */
	
	/**
	 * Default sentence counter event driver constructor.
	 */
	public SentenceCounterEventDriver() {
		sentencesDriver = new SentenceEventDriver();
	}
	
	/* ==================
	 * overriding methods
	 * ==================
	 */
	
	public String displayName() {
		return "Sentence count";
	}

	public String tooltipText() {
		return "The total number of sentences";
	}

	public boolean showInGUI() {
		return true;
	}

	public double getValue(char[] text) throws EventGenerationException {
		return sentencesDriver.createEventSet(text).size();
	}

	@Override
	public EventSet createEventSet(char[] text) throws EventGenerationException {
		EventSet res = new EventSet();
		res.addEvent(new Event(getValue(text)+"", this));
		//System.out.println(getValue(text));
		return res;
	}
}
