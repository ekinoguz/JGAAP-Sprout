package com.jgaap.eventDrivers;

import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.NumericEventDriver;
import com.jgaap.generics.NumericEventSet;

public class NF_GunningFogIndexEventDriver extends NumericEventDriver {

	/* ======
	 * fields
	 * ======
	 */
	
	/**
	 * Event drivers to be used.
	 */
	private NF_WordCounterEventDriver wordCounter;
	
	private NF_SentenceCounterEventDriver sentenceCounter;
	
	private EventDriver syllablesDriver;
	
	
	/* ============
	 * constructors
	 * ============
	 */
	
	/**
	 * Default Gunning-Fog readability index event driver constructor.
	 */
	public NF_GunningFogIndexEventDriver() {
		wordCounter = new NF_WordCounterEventDriver();
		sentenceCounter = new NF_SentenceCounterEventDriver();
		syllablesDriver = new WordSyllablesEventDriver();
	}
	
	/* ==================
	 * overriding methods
	 * ==================
	 */
	
	public String displayName() {
		return "9F_Gunning-Fog Readability Index";
	}

	public String tooltipText() {
		return "Gunning-Fog Readability Index";
	}

	public boolean showInGUI() {
		return true;
	}

	public double getValue(char[] text) throws EventGenerationException {
		double wordCount = wordCounter.getValue(text);
		double sentenceCount = sentenceCounter.getValue(text);
		EventSet syllables = syllablesDriver.createEventSet(text);
		for (int i=syllables.size()-1; i>=0; i--) {
			if (Integer.parseInt(syllables.eventAt(i).toString()) < 3)
				syllables.removeEvent(syllables.eventAt(i));
		}
		double complexWordsCount = syllables.size();
		return 0.4*(wordCount/sentenceCount + 100*complexWordsCount/wordCount);
	}

	@Override
	public NumericEventSet createEventSet(char[] text) throws EventGenerationException {
		
		NumericEventSet res = new NumericEventSet();
		res.addEvent(new Event(getValue(text)+"", this));
		//System.out.println(getValue(text));
		return res;
	}
}
