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
package com.jgaap.canonicizers;

import com.jgaap.generics.Canonicizer;

/**
 * Used to separate canonocizers for each eventDriver
 *  
 * @author ekinoguz
 * 
 */
public class SeparatorCanonicizer extends Canonicizer {

	@Override
	public String displayName() {
		return "Separator";
	}

	@Override
	public String tooltipText() {
		return "Used to separate canonocizers for each eventDriver";
	}

	@Override
	public String longDescription() {
		return "Used to separate canonocizers for each eventDriver. Does nothing";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}


	@Override
	public char[] process(char[] procText) {
		return procText;
	}

}
