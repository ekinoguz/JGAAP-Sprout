package com.jgaap.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.common.collect.ImmutableMap;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventMap;

public class CompareMatching {

	HashMap<String, LinkedHashMap<Event, Double>> histogram;
	
	public CompareMatching()
	{
		saveSortedFiles();
	}

	private void saveSortedFiles() 
	{
		histogram = unionAll();
		HashMap<String, String> outputs = new HashMap<String, String>();
		for (String str : histogram.keySet())
		{
			StringBuilder sb = new StringBuilder();

			// if numeric event, then just print the value
			if (EventDriverFactory.isNumeric(getEventDriverName(str)))
				sb.append(str + " = " + histogram.get(str).keySet()+"\n");
			else
				sb.append(str + " = " + histogram.get(str)+"\n");
			
			String tmp = "";
			String eventDriverName = getEventDriverName(str);
			if (outputs.containsKey(eventDriverName))
			{
				tmp = outputs.get(eventDriverName);
			}
			tmp = tmp + sb.toString();
			outputs.put(eventDriverName, tmp);
		}
		for (String str : outputs.keySet())
		{
			String path = getPath(str);
			//System.out.println(path);
			//System.out.println(outputs.get(str));
			Utils.saveFile(path, outputs.get(str));
		}
	}
	
	
	public HashMap<String, LinkedHashMap<Event, Double>> unionAll() {
		HashMap<String, LinkedHashMap<Event, Double>> out = new HashMap<String, LinkedHashMap<Event,Double>>();
		for (String d : EventMap.getValueHistogram().keySet())
		{
			ImmutableMap<Event, Double> immutable = EventMap.getValueHistogram().get(d);
			HashMap<Event, Double> hashmap = new HashMap<Event, Double>();
			
			// add all the values from immutable map
			for (Event e : immutable.keySet())
				hashmap.put(e, immutable.get(e));
			
			// add 0 values all other immutable maps if entry is not in hashmap
			for ( String otherDoc : EventMap.getValueHistogram().keySet())
			{
				if (!otherDoc.equals(d) && compareEnding(d, otherDoc))
				{
					// check if it is a numeric event driver
					if (EventDriverFactory.isNumeric(getEventDriverName(otherDoc)))
						continue;
					
					immutable = EventMap.getValueHistogram().get(otherDoc);
					for (Event e : immutable.keySet())
					{
						if (!hashmap.containsKey(e))
							hashmap.put(e, 0.0);
					}
				}
			}
			
			/* Sort the hashmap */
			// Get entries and sort them.
			List<Entry<Event, Double>> entries = new ArrayList<Entry<Event, Double>>(hashmap.entrySet());
			Collections.sort(entries, new Comparator<Entry<Event, Double>>() {
			    public int compare(Entry<Event, Double> e1, Entry<Event, Double> e2) {
			        return e1.getKey().compareTo(e2.getKey());
			    }
			});
			
			// Put entries back in an ordered map.
			LinkedHashMap<Event, Double> orderedMap = new LinkedHashMap<Event, Double>();
			for (Entry<Event, Double> entry : entries) {
			    orderedMap.put(entry.getKey(), entry.getValue());
			}
			
			// add the <d, hashmap> to out hashmap
			out.put(d, orderedMap);
			
		}
		
		return out;
	}
	
	
	private String getPath(String filename) {
		int lastIndex = ExperimentEngine.outputFileName.get(0).lastIndexOf('/');
		filename = ExperimentEngine.outputFileName.get(0).substring(0, lastIndex+1) + "CM-" + filename;
		return filename;
	}
	
	
	private boolean compareEnding(String s1, String s2)
	{
		int lastIndex1 = s1.lastIndexOf('|');
		int lastIndex2 = s2.lastIndexOf('|');
		return (s1.substring(lastIndex1+1)).equals(s2.substring(lastIndex2+1));
	}
	
	private String getEventDriverName(String s)
	{
		int lastIndex = s.lastIndexOf('|');
		return s.substring(lastIndex+1);
	}
	
}
