package com.mattwelke.beamcustomaggexample;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

public class Utils {
    public static String currTimeIso() {
        String currTimeStr = ISODateTimeFormat.dateTime().print(new DateTime());
        return currTimeStr;
    }
}
