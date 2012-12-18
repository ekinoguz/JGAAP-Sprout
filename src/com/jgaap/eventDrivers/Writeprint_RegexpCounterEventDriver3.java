package com.jgaap.eventDrivers;

import java.util.regex.*;

import com.jgaap.generics.*;

public class Writeprint_RegexpCounterEventDriver3 extends EventDriver {

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
		return "_WP_Regular expr counter, [A-Z]";
	}

	public String tooltipText() {
		return "The frequency of matches of a given regular expression in the document. [A-Z]";
	}

	public boolean showInGUI() {
		return true;
	}

	public double getValue(char[] text) {
		// set regexp parameter
		// regexp = getParameter("regexp");
		double total = text.length;
		regexp = "[A-Z]";
		if (regexp.equals(""))
			regexp = ".";
		
		Pattern p = Pattern.compile(regexp);
		Matcher m = p.matcher(new String(text));
		int count = 0;
		while (m.find())
			count++;
		return Math.round(1000 * count/total);
	}
	
	public EventSet createEventSet(char[] text) throws EventGenerationException {
		
		EventSet res = new EventSet();
		res.addEvent(new Event(getValue(text)+"", this));
		//System.out.println(getValue(text));
		return res;
	}
}
