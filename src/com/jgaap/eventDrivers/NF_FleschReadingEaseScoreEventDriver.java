package com.jgaap.eventDrivers;

import com.jgaap.generics.Event;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.generics.NumericEventDriver;
import com.jgaap.generics.NumericEventSet;

public class NF_FleschReadingEaseScoreEventDriver extends NumericEventDriver {

	/* ======
	 * fields
	 * ======
	 */
	
	/**
	 * Event drivers to be used.
	 */
	private NF_WordCounterEventDriver wordCounter;
	
	private NF_SentenceCounterEventDriver sentenceCounter;
	
	private NF_SyllableCounterEventDriver syllablesCounter;
	
	
	/* ============
	 * constructors
	 * ============
	 */
	
	/**
	 * Default Gunning-Fog readability index event driver constructor.
	 */
	public NF_FleschReadingEaseScoreEventDriver() {
		wordCounter = new NF_WordCounterEventDriver();
		sentenceCounter = new NF_SentenceCounterEventDriver();
		syllablesCounter = new NF_SyllableCounterEventDriver();
	}
	
	/* ==================
	 * overriding methods
	 * ==================
	 */
	
	public String displayName() {
		return "9F_Flesch Reading Ease Score";
	}

	public String tooltipText() {
		//return "The Flesch reading ease score: 206.835 - 1.015*(total words / total sentences) -84.6*(total syllables / total words)";
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
	public NumericEventSet createEventSet(char[] text) throws EventGenerationException {
		
		NumericEventSet res = new NumericEventSet();
		res.addEvent(new Event(getValue(text)+"", this));
		//System.out.println(getValue(text));
		return res;
	}
}
