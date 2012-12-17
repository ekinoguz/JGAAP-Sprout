package com.jgaap.eventDrivers;

import com.jgaap.generics.*;

public class FleschReadingEaseScoreEventDriver extends EventDriver {

	/* ======
	 * fields
	 * ======
	 */
	
	/**
	 * Event drivers to be used.
	 */
	private WordCounterEventDriver wordCounter;
	
	private SentenceCounterEventDriver sentenceCounter;
	
	private SyllableCounterEventDriver syllablesCounter;
	
	
	/* ============
	 * constructors
	 * ============
	 */
	
	/**
	 * Default Gunning-Fog readability index event driver constructor.
	 */
	public FleschReadingEaseScoreEventDriver() {
		wordCounter = new WordCounterEventDriver();
		sentenceCounter = new SentenceCounterEventDriver();
		syllablesCounter = new SyllableCounterEventDriver();
	}
	
	/* ==================
	 * overriding methods
	 * ==================
	 */
	
	public String displayName() {
		return "Flesch Reading Ease Score";
	}

	public String tooltipText() {
		return "Flesch Reading Ease Score";
	}

	public boolean showInGUI() {
		return true;
	}

	public double getValue(char[] text) throws EventGenerationException {
		double wordCount = wordCounter.getValue(text);
		double sentenceCount = sentenceCounter.getValue(text);
		double syllableCount = syllablesCounter.getValue(text);
		return 206.835 - 1.015*wordCount/sentenceCount - 84.6*syllableCount/wordCount;
	}
	
	@Override
	public EventSet createEventSet(char[] text) throws EventGenerationException {
		EventSet res = new EventSet();
		res.addEvent(new Event(getValue(text)+"", this));
		//System.out.println(getValue(text));
		return res;
	}
}
