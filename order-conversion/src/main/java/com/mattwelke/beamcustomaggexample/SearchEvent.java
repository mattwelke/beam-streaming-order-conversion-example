package com.mattwelke.beamcustomaggexample;

import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.DefaultCoder;

@DefaultCoder(AvroCoder.class)
public class SearchEvent {
    public String searchTerm;
    public String[] searchResultProductIds;
}
