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
package com.jgaap.backend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jgaap.generics.EventDriver;
/**
 * 
 * Instances new Event Drivers based on display name.
 * If parameters are also passed in the form DisplayName|name:value|name:value they are added to the Event Driver before it is returned
 *
 * @author Michael Ryan
 * @since 5.0.0
 */

public class EventDriverFactory {

	private static final Map<String, EventDriver> eventDrivers = loadEventDrivers();
	private static final Map<String, Integer> numerics = loadNumerics(); 
	
	private static Map<String, Integer> loadNumerics() {
		// Load the event drivers dynamically
		Map<String, Integer> eventDrivers = new HashMap<String, Integer>();
		eventDrivers.put(("9F_Average Sentence Length").toLowerCase(), 1);
		eventDrivers.put(("9F_Average Syllables in Word").toLowerCase(), 1);
		eventDrivers.put(("9F_Character Space").toLowerCase(), 1);
		eventDrivers.put(("9F_Complexity").toLowerCase(), 1);
		eventDrivers.put(("9F_Flesch Reading Ease Score").toLowerCase(), 1);
		eventDrivers.put(("9F_Gunning-Fog Readability Index").toLowerCase(), 1);
		eventDrivers.put(("9F_Letter Space").toLowerCase(), 1);
		eventDrivers.put(("9F_Sentence Count").toLowerCase(), 1);
		eventDrivers.put(("9F_Unique Words Count").toLowerCase(), 1);
		
		// Write prints
		eventDrivers.put(("_WP_Average characters per word").toLowerCase(), 1);
		eventDrivers.put(("_WP_Character Count").toLowerCase(), 1);
		eventDrivers.put(("_WP_Uppercase Letters Percentage").toLowerCase(), 1);
		eventDrivers.put(("_WP_Letters Percentage").toLowerCase(), 1);
		
		return eventDrivers;
	}
	
	private static Map<String, EventDriver> loadEventDrivers() {
		// Load the event drivers dynamically
		Map<String, EventDriver> eventDrivers = new HashMap<String, EventDriver>();
		for(EventDriver eventDriver : EventDriver.getEventDrivers()){
			eventDrivers.put(eventDriver.displayName().toLowerCase().trim(), eventDriver);
		}
		return eventDrivers;
	}
	
	public static EventDriver getEventDriver(String action) throws Exception{
		EventDriver eventDriver;
		List<String[]> parameters = Utils.getParameters(action);
		action = parameters.remove(0)[0].toLowerCase().trim();
		if(eventDrivers.containsKey(action)){
			eventDriver = eventDrivers.get(action).getClass().newInstance();
		}else{
			throw new Exception("Event Driver "+action+" not found!");
		}
		for(String[] parameter : parameters){
			eventDriver.setParameter(parameter[0].trim(), parameter[1].trim());
		}
		return eventDriver;
	}
	
	public static String getEventDriverDisplayName(String target) {
		for (String s:eventDrivers.keySet())
		{
			if ((eventDrivers.get(s).toString()).contains(target))
				return s;
		}
		return "error-in-eventdriverfactor";
	}
	
	public static boolean isNumeric(String s)
	{
		s = s.toLowerCase();
		if (numerics.containsKey(s))
			return true;
		else
			return false;
	}
}
