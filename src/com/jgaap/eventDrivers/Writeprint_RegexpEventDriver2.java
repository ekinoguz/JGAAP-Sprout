package com.jgaap.eventDrivers;

import java.util.regex.*;

import com.jgaap.generics.*;

public class Writeprint_RegexpEventDriver2 extends EventDriver {

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
		return "_WP_Regular expression matches, \\d\\d";
	}

	public String tooltipText() {
		return "The frequencies of all distinct matches of a given regular expression in the document.";
	}

	public boolean showInGUI() {
		return true;
	}
	
	@Override
	public EventSet createEventSet(char[] text) {
		//EventSet es = new EventSet(doc.getAuthor());
		EventSet es = new EventSet();
		// set regexp parameter
		// regexp = getParameter("regexp");
		regexp = "\\d\\d";
		if (regexp.equals(""))
			regexp = ".";

		Pattern p = Pattern.compile(regexp);
		Matcher m = p.matcher(new String(text));
		while (m.find())
			es.addEvent(new Event(m.group(), this));
		
		return es;
	}
}
