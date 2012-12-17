package com.jgaap.eventDrivers;

import com.jgaap.eventDrivers.*;
import com.jgaap.generics.*;

public class WordCounterEventDriver extends EventDriver {

	/* ======
	 * fields
	 * ======
	 */
	
	/**
	 * Event driver to be used for word count.
	 */
	private EventDriver wordsDriver;
	
	
	/* ============
	 * constructors
	 * ============
	 */
	
	/**
	 * Default sentence counter event driver constructor.
	 */
	public WordCounterEventDriver() {
		wordsDriver = new NaiveWordEventDriver();
	}
	
	/* ==================
	 * overriding methods
	 * ==================
	 */
	
	public String displayName() {
		return "Word count";
	}

	public String tooltipText() {
		return "The total number of words";
	}

	public boolean showInGUI() {
		return true;
	}

	public double getValue(char[] text) throws EventGenerationException {
		return wordsDriver.createEventSet(text).size();
	}

	@Override
	public EventSet createEventSet(char[] text) throws EventGenerationException {
		EventSet res = new EventSet();
		res.addEvent(new Event(getValue(text)+"", this));
		//System.out.println(getValue(text));
		return res;
	}
}
