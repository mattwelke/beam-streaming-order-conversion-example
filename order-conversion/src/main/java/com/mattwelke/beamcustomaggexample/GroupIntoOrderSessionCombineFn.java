package com.mattwelke.beamcustomaggexample;

import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.DefaultCoder;
import org.apache.beam.sdk.transforms.Combine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GroupIntoOrderSessionCombineFn extends Combine.CombineFn<Event, GroupIntoOrderSessionCombineFn.Accum, OrderSession> {
    @DefaultCoder(AvroCoder.class)
    public static class Accum implements Serializable {
        List<Event> searchEvents = new ArrayList<>();
        List<Event> orderEvents = new ArrayList<>();
    }

    @Override
    public GroupIntoOrderSessionCombineFn.Accum createAccumulator() {
        return new GroupIntoOrderSessionCombineFn.Accum();
    }

    @Override
    public GroupIntoOrderSessionCombineFn.Accum addInput(GroupIntoOrderSessionCombineFn.Accum accum, Event input) {
        if (input.eventType.equals("search")) {
            accum.searchEvents.add(input);
        }
        if (input.eventType.equals("order")) {
            accum.orderEvents.add(input);
        }
        return accum;
    }

    @Override
    public GroupIntoOrderSessionCombineFn.Accum mergeAccumulators(Iterable<GroupIntoOrderSessionCombineFn.Accum> accums) {
        GroupIntoOrderSessionCombineFn.Accum merged = createAccumulator();
        for (GroupIntoOrderSessionCombineFn.Accum accum : accums) {
            merged.searchEvents.addAll(accum.searchEvents);
            merged.orderEvents.addAll(accum.orderEvents);
        }
        return merged;
    }

    @Override
    public OrderSession extractOutput(GroupIntoOrderSessionCombineFn.Accum accum) {
        OrderSession s = new OrderSession();
        s.searchEvents = accum.searchEvents;
        s.orderEvents = accum.orderEvents;
        return s;
    }
}
