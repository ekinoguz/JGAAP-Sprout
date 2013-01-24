/*
 * JGAAP -- a graphical program for stylometric authorship attribution
 * Copyright (C) 2009,2011 by Patrick Juola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 **/
package com.jgaap.eventDrivers;

import com.jgaap.generics.EventGenerationException;
import com.jgaap.generics.EventSet;

/**
 * Extract character N-grams as features.
 * 
 */
public class Writeprint_WordNGramEventDriver1 extends NGramEventDriver {

	public Writeprint_WordNGramEventDriver1() {
		addParams("N", "N", "2", new String[] { "1", "2", "3", "4", "5", "6",
				"7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
				"18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
				"28", "29", "30", "31", "32", "33", "34", "35", "36", "37",
				"38", "39", "40", "41", "42", "43", "44", "45", "46", "47",
				"48", "49", "50" }, false);
	}

	@Override
	public String displayName() {
		return "_WP_Word Bigrams";
	}

	@Override
	public String tooltipText() {
		return "Groups of N Successive Words";
	}

	@Override
	public String longDescription() {
		return "Groups of N successive words (using sliding window)";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	private NGramEventDriver theDriver = new NGramEventDriver();

	@Override
	public EventSet createEventSet(char[] text) throws EventGenerationException {
		this.setParameter("N", 2);
		String temp = this.getParameter("N");
		if (temp.equals("")) {
			this.setParameter("N", 2);
		}
		theDriver.setParameter("N", this.getParameter("N"));
		return theDriver.createEventSet(text);
	}
}
