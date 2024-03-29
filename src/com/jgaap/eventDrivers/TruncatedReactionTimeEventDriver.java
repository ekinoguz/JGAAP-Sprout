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

import com.jgaap.backend.API;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.generics.EventSet;

/**
 * Truncate lexical frequency for discrete binning 
 *
 */
public class TruncatedReactionTimeEventDriver extends EventDriver {

    @Override
    public String displayName(){
    	return "Binned Reaction Times";
    }
    
    @Override
    public String tooltipText(){
    	return "Discretized (by truncation) ELP lexical decision latencies";
    }
    
    @Override
    public boolean showInGUI(){
    	return API.getInstance().getLanguage().getLanguage().equalsIgnoreCase("english");
    }

    private EventDriver theDriver = new TruncatedEventDriver();


    @Override
    public EventSet createEventSet(char[] text) throws EventGenerationException {
        theDriver.setParameter("length", "2");
        theDriver.setParameter("underlyingEvents", "Lexical Decision Reaction Times");
        return theDriver.createEventSet(text);
    }
}
