package com.jgaap.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import com.google.common.collect.ImmutableMap;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventMap;

public class CompareMatching {

	HashMap<String, LinkedHashMap<Event, Double>> histogram;
	
	/**
	 * 
	 * @param option
	 * 		0: cross matching: cm
	 * 		1: cross matching just values: cmjv
	 */
	public CompareMatching(int option)
	{
		if (option == 0)
			saveSortedFilesAccordingToEventDriver();
		else if (option == 1)
			saveSortedValuesUsingFilename();
	}

	public void saveSortedValuesUsingFilename() 
	{
		histogram = unionAll();
		HashMap<String, String> outputs = new HashMap<String, String>();
		String path;
		int lastIndex = ExperimentEngine.outputFileName.get(0).lastIndexOf('/');
		String pathBeginning = ExperimentEngine.outputFileName.get(0).substring(0, lastIndex+1);
		
		ArrayList<String> eventDrivers = new ArrayList<String>();
		for (String str : histogram.keySet())
		{
			String tmp = getEventDriverName(str);
			if (!eventDrivers.contains(tmp))
				eventDrivers.add(tmp);
		}
		
		for (String str : histogram.keySet())
		{
			StringBuilder sb = new StringBuilder();
			
			for (String ed : eventDrivers)
			{
				path = getFilePath(str) + '|' + ed;
				// if numeric event, then just print the keySet which is a value
				if (EventDriverFactory.isNumeric(getEventDriverName(ed)))
					sb.append(histogram.get(path).keySet()+" ");
				// otherwise just print the value
				else
					sb.append(histogram.get(path).values()+" ");
			}
			String fileName = getFileName(str);
			if (!outputs.containsKey(fileName))
			{
				outputs.put(fileName, "true");
				fileName = pathBeginning + fileName + "-" + ExperimentEngine.outputNumber;
				String writeData = organizeOutput(sb.toString());
				//System.out.println(fileName);
				//System.out.println(writeData);
				Utils.saveFile(fileName, writeData);
			}
		}
	}
	
	public void saveSortedFilesAccordingToEventDriver() 
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
	
	private String organizeOutput(String s)
	{
		StringTokenizer st = new StringTokenizer(s, "[],");
		String output = "";
		//System.out.println("******token count: " + st.countTokens());
		while (st.hasMoreTokens())
			output += st.nextToken();
		return output;
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
	
	private String getFileName(String s)
	{
		int lastIndex = s.lastIndexOf('|');
		int beginIndex = s.lastIndexOf('/');
		return s.substring(beginIndex+1, lastIndex);
	}
	
	private String getFilePath(String s)
	{
		int lastIndex = s.lastIndexOf('|');
		return s.substring(0, lastIndex);
	}
	
}
