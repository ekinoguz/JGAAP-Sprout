package com.jgaap.eventDrivers;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventSet;
import com.jgaap.ui.JGAAP_UI_MainForm;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 * This changes words into their parts of speech in a document, based on Stanford's MaxentTagger.
 * 
 * @author Ariel Stolerman
 */

public class Writeprint_MaxentPOSTagsEventDriver extends EventDriver {

	@Override
	public String displayName() {
		return "_WP_POS Tags";
	}

	@Override
	public String tooltipText() {
		return "Stanford Log-linear Part-Of-Speech Tagger";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}
	
	protected static MaxentTagger tagger = null;
	protected static String taggerPath = "com/jgaap/resources/models/postagger/english-left3words-distsim.tagger";
	
	@SuppressWarnings("static-access")
	@Override
	public EventSet createEventSet(char[] text) {
		//EventSet es = new EventSet(doc.getAuthor());
		EventSet es = new EventSet();
		String stringText = new String(text);
		
		// initialize tagger and return empty event set if encountered a problem
		if (tagger == null) {
			tagger = initTagger();
			if (tagger == null) return es;
		}

		List<List<HasWord>> sentences = tagger.tokenizeText(new BufferedReader(new StringReader(stringText)));
		for (List<HasWord> sentence : sentences) {
			ArrayList<TaggedWord> tSentence = tagger.tagSentence(sentence);
			for (TaggedWord tw: tSentence)
				es.addEvent(new Event(tw.tag(), this));
		}
		
		return es;
	}
	
	/**
	 * Initialize the tagger.
	 * @return
	 */
	public static MaxentTagger initTagger() {
		MaxentTagger t = null;
		try {
			//tagger = new MaxentTagger();
			t = new MaxentTagger(taggerPath);
		} catch (Exception e) {
			Logger logger = Logger.getLogger(JGAAP_UI_MainForm.class);
			logger.error("MaxentTagger failed to load tagger from ",e);
			
			//Logger.logln("MaxentTagger failed to load tagger from ",LogOut.STDERR);
			e.printStackTrace();
			System.out.println("YOU SHOULD NOT SEE THIS - in Writeprint_MaxentPOSTagsEventDriver");
		}
		return t;
	}
}
