package com.mattwelke.beamcustomaggexample;

import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.DefaultCoder;

import java.util.ArrayList;
import java.util.List;

/**
 * A session of beacons containing all of its Search events and Order events.
 */
@DefaultCoder(AvroCoder.class)
public class OrderSession {
    List<Event> searchEvents = new ArrayList<>();
    List<Event> orderEvents = new ArrayList<>();
}
