package com.mattwelke.beamcustomaggexample;

import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.DefaultCoder;

import java.util.List;

@DefaultCoder(AvroCoder.class)
public class OrderSessionAttributions {
    List<String> attributedSearchTerms;
    List<String> nonAttributedSearchTerms;
}
