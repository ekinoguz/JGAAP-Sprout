package com.jgaap.eventDrivers;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventSet;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 * This changes words into their parts of speech in a document, based on Stanford's MaxentTagger.
 * 
 * @author Ariel Stolerman
 */

public class Writeprint_MaxentPOSNGramsEventDriver2 extends EventDriver {

	@Override
	public String displayName() {
		return "_WP_Maxent POS N-Grams, n=3";
	}

	@Override
	public String tooltipText() {
		return "Stanford Log-linear Part-Of-Speech Tagger for POS N-grams";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	private static MaxentTagger tagger = null;
	
	@SuppressWarnings("static-access")
	@Override
	public EventSet createEventSet(char[] text) {
		//EventSet es = new EventSet(doc.getAuthor());
		EventSet es = new EventSet();
		String stringText = new String(text);

		// use MaxentPOSTagsEventDriver's tagger
		// initialize tagger and return empty event set if encountered a problem
		if (tagger == null) {
			tagger = Writeprint_MaxentPOSTagsEventDriver.initTagger();
			if (tagger == null) return es;
		}
		

		List<List<HasWord>> sentences = tagger.tokenizeText(new BufferedReader(new StringReader(stringText)));
		ArrayList<TaggedWord> tagged = new ArrayList<TaggedWord>();
		for (List<HasWord> sentence : sentences)
			tagged.addAll(tagger.tagSentence(sentence));
		
		int i,j,n;
		try {
			//n = Integer.parseInt(getParameter("N"));
			n = 3;
		} catch (NumberFormatException e) {
			n = 2;
		}
		String curr;
		for (i=0; i<tagged.size()-n+1; i++) {
			curr = "("+tagged.get(i).tag()+")";
			for (j=1; j<n; j++) {
				curr += "-("+tagged.get(i+j).tag()+")";
			}
			es.addEvent(new Event(curr, this));
		}
		
		return es;
	}
	
	/*
	// main for testing
	public static void main(String[] args) throws Exception {
		Document doc = new Document("./corpora/drexel_1/a/a_01.txt","a","a_01.txt");
		doc.load();
		MaxentPOSNGramsEventDriver m = new MaxentPOSNGramsEventDriver();
		m.setParameter("N", 2);
		EventSet es = m.createEventSet(doc);
		for (int i=0; i<es.size(); i++) {
			System.out.print(es.eventAt(i)+" ");
			if (i % 30 == 0) System.out.println();
		}
	}
	*/
}
