package com.jgaap.eventDrivers;

import com.jgaap.eventDrivers.*;
import com.jgaap.generics.*;

public class GunningFogIndexEventDriver extends EventDriver {

	/* ======
	 * fields
	 * ======
	 */
	
	/**
	 * Event drivers to be used.
	 */
	private WordCounterEventDriver wordCounter;
	
	private SentenceCounterEventDriver sentenceCounter;
	
	private EventDriver syllablesDriver;
	
	
	/* ============
	 * constructors
	 * ============
	 */
	
	/**
	 * Default Gunning-Fog readability index event driver constructor.
	 */
	public GunningFogIndexEventDriver() {
		wordCounter = new WordCounterEventDriver();
		sentenceCounter = new SentenceCounterEventDriver();
		syllablesDriver = new WordSyllablesEventDriver();
	}
	
	/* ==================
	 * overriding methods
	 * ==================
	 */
	
	public String displayName() {
		return "Gunning-Fog Readability Index";
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
	public EventSet createEventSet(char[] text) throws EventGenerationException {
		EventSet res = new EventSet();
		res.addEvent(new Event(getValue(text)+"", this));
		//System.out.println(getValue(text));
		return res;
	}
}
