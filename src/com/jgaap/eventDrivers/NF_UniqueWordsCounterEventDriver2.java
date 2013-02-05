package com.jgaap.eventDrivers;

import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.NumericEventDriver;
import com.jgaap.generics.NumericEventSet;

public class NF_UniqueWordsCounterEventDriver2 extends NumericEventDriver {

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
	public NF_UniqueWordsCounterEventDriver2() {
		wordCounter = new NaiveWordEventDriver();
	}
	
	/* ==================
	 * overriding methods
	 * ==================
	 */
	
	public String displayName() {
		return "9F_Complexity";
	}

	public String tooltipText() {
		return "Ratio of unique words to total number of words in the document.";
	}

	public boolean showInGUI() {
		return true;
	}

	public double getValue(char[] text) throws EventGenerationException {
		EventSet words = wordCounter.createEventSet(text);
		double total = words.size();
		return (new EventHistogram(words)).getNTypes()/total;
	}
	
	@Override
	public NumericEventSet createEventSet(char[] text) throws EventGenerationException {
		
		NumericEventSet res = new NumericEventSet();
		res.addEvent(new Event(getValue(text)+"", this));
		//System.out.println(getValue(text));
		return res;
	}
}
