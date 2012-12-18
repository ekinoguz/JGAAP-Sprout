package com.jgaap.eventDrivers;

import com.jgaap.eventDrivers.CharacterNGramEventDriver;
import com.jgaap.generics.*;

public class Writeprint_LetterNGramEventDriver3 extends CharacterNGramEventDriver {
	
	@Override
	public String displayName() {
		return "_WP_Letter N-Grams, N=3";
	}

	@Override
	public String tooltipText() {
		return "Groups of N successive letters, N = 3";
	}

	@Override
	public String longDescription() {
		return "Groups of N successive letters (sliding window); N is given as a parameter.";
	}

	@Override
	public EventSet createEventSet(char[] text) {
		int n;
		try {
			// n = Integer.parseInt(getParameter("N"));
			n = 3;
		} catch (NumberFormatException e) {
			n = 2;
		}
		EventSet eventSet = new EventSet(text.length);
		String curr;
		for (int i = 0; i <= text.length - n; i++) {
			curr = new String(text, i, n);
			if (curr.matches("[A-Za-z]+"))
				eventSet.addEvent(new Event(curr, this));
		}
		return eventSet;
	}
}
