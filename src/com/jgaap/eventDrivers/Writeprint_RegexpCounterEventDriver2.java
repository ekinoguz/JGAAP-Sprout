package com.jgaap.eventDrivers;

import java.util.regex.*;

import com.jgaap.generics.*;

public class Writeprint_RegexpCounterEventDriver2 extends NumericEventDriver {

	/* ======
	 * fields
	 * ======
	 */
	
	private String regexp;
	
	/* ==================
	 * overriding methods
	 * ==================
	 */
	
	public String displayName() {
		return "_WP_Regular expr counter [A-Za-z]";
	}

	public String tooltipText() {
		return "The frequency of matches of a given regular expression in the document. [A-Za-z]";
	}

	public boolean showInGUI() {
		return true;
	}

	public double getValue(char[] text) {
		double total = text.length;
		// set regexp parameter
		// regexp = getParameter("regexp");
		regexp = "[A-Za-z]";
		if (regexp.equals(""))
			regexp = ".";
		
		Pattern p = Pattern.compile(regexp);
		Matcher m = p.matcher(new String(text));
		int count = 0;
		while (m.find())
			count++;
		
		return Math.round(1000 * count/total);
	}
	
	@Override
	public NumericEventSet createEventSet(char[] text) throws EventGenerationException {
		
		NumericEventSet res = new NumericEventSet();
		res.addEvent(new Event(getValue(text)+"", this));
		//System.out.println(getValue(text));
		return res;
	}
}
