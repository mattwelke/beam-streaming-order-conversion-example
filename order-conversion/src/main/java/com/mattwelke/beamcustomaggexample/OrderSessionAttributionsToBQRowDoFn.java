package com.mattwelke.beamcustomaggexample;

import com.google.api.services.bigquery.model.TableRow;
import org.apache.beam.sdk.transforms.DoFn;

public class OrderSessionAttributionsToBQRowDoFn extends DoFn<OrderSessionAttributions, TableRow> {
    @ProcessElement
    public void processElement(@Element OrderSessionAttributions input, OutputReceiver<TableRow> out) {
        TableRow row = new TableRow();
        row.set("timestamp", Utils.currTimeIso());
        row.set("attributed_search_terms", input.attributedSearchTerms);
        row.set("non_attributed_search_terms", input.nonAttributedSearchTerms);
        out.output(row);
    }
}
