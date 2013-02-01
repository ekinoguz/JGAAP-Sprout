package com.jgaap.generics;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;

/**
 * An Immutable mapping of Events to their relative frequencies
 * 
 * @author Michael Ryan
 *
 */
public class EventMap {

	private final ImmutableMap<Event, Double> histogram;
	/* ekinoguz */
	private static HashMap<String, ImmutableMap<Event, Double>> valueHistograms = new HashMap<String, ImmutableMap<Event,Double>>();
	/* ekinoguz */
	
	public EventMap(Document document) {
		this(document.getEventSets().values());
		updateValueHistograms(document.getEventSets(), document.getFilePath());
	}
	
	public EventMap(Iterable<EventSet> eventSets) {
		Builder<Event, Double> histogramBuilder = ImmutableMap.builder();
		for(EventSet eventSet : eventSets){
			double numEvents = eventSet.size();
			Multiset<Event> multiset = ImmutableMultiset.copyOf(eventSet); 
			for (Entry<Event> eventEntry : multiset.entrySet()) {
				histogramBuilder.put(eventEntry.getElement(), eventEntry.getCount()/numEvents);
			}
		}
		histogram = histogramBuilder.build();
	}
	
	public void updateValueHistograms(Map<EventDriver, EventSet> documentMap, String documentPath) {
		for (EventDriver ed : documentMap.keySet())
		{
			String filePath = getEventDriverName(documentPath, ed.displayName());
			//System.out.println(filePath);
			ImmutableMap<Event, Double> valueHistogram;
			Builder<Event, Double> valueHistogramBuilder = ImmutableMap.builder();
			Multiset<Event> multiset = ImmutableMultiset.copyOf(documentMap.get(ed)); 
			for (Entry<Event> eventEntry : multiset.entrySet()) {
				valueHistogramBuilder.put(eventEntry.getElement(), (double) eventEntry.getCount());
			}
			valueHistogram = valueHistogramBuilder.build();
			valueHistograms.put(filePath, valueHistogram);
		}
	}
	
	public EventMap(EventSet eventSet) {
		this(Collections.singleton(eventSet));
	}
	
	/* ekinoguz */
	/*
	 * added valueHistogram and valueHistogramBuilder 
	 */
	public static HashMap<String, ImmutableMap<Event, Double>> getValueHistogram() {
		return valueHistograms;
	}
	public String getEventDriverName(String documentPath, String eventDriverName)
	{
		return documentPath + "|" + eventDriverName;
	}
	/* ekinoguz */
	
	public EventMap(Map<Event, Double> histogram){
		this.histogram = ImmutableMap.copyOf(histogram);
		valueHistograms = null; /* ekinoguz TODO: check here */
	}

	public double relativeFrequency(Event event) {
		Double frequency = histogram.get(event);
		if (frequency == null) {
			return 0.0;
		} else {
			return frequency.doubleValue();
		}
	}
		
	public double normalizedFrequency(Event event) {
		Double frequency = histogram.get(event);
		if (frequency == null) {
			return 0.0;
		} else {
			return frequency * 100000;
		}
	}
	
	public boolean contains(Event event) {
		return histogram.containsKey(event);
	}

	public Set<Event> uniqueEvents() {
		return histogram.keySet();
	}
	
	public static EventMap centroid(List<EventMap> eventMaps) {
		ImmutableMultimap.Builder<Event, Double> multiMapBuilder = ImmutableMultimap.builder();
		for (EventMap eventMap : eventMaps) {
			for(Map.Entry<Event, Double> entry : eventMap.histogram.entrySet()){
				multiMapBuilder.put(entry.getKey(), entry.getValue());
			}
		}
		ImmutableMultimap<Event, Double> multimap = multiMapBuilder.build();
		
		double count = eventMaps.size();
		Builder<Event, Double> builder = ImmutableMap.builder();
		for (Map.Entry<Event, Collection<Double>> entry : multimap.asMap().entrySet()) {
			double value = 0.0;
			for(double current : entry.getValue()){
				value += current;
			}
			builder.put(entry.getKey(), value/count);
		}
		return new EventMap(builder.build());
	}
}
