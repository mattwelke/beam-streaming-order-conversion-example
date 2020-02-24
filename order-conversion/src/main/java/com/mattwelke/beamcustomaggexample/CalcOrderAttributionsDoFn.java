package com.mattwelke.beamcustomaggexample;

import org.apache.beam.sdk.transforms.DoFn;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Transforms an order session into a map of search terms present in the session and
 * the number of times the search term included products in search results that were
 * ordered in the session.
 */
public class CalcOrderAttributionsDoFn extends DoFn<OrderSession, OrderSessionAttributions> {
    @ProcessElement
    public void processElement(@Element OrderSession input, OutputReceiver<OrderSessionAttributions> out) {
        // Track processed search terms to avoid wasting work.
        List<String> processedSearchTerms = new ArrayList<>();

        //  Will build and return after algorithm.
        List<String> attributedSearchTerms = new ArrayList<>();
        List<String> nonAttributedSearchTerms = new ArrayList<>();

        // Every search term starts as non-attributed.
        for (Event search : input.searchEvents) {
            String searchTerm = search.searchEvent.searchTerm;
            if (!nonAttributedSearchTerms.contains(searchTerm)) {
                nonAttributedSearchTerms.add(searchTerm);
            }
        }

        // Find all ordered products.
        List<String> orderedProductIds = input.orderEvents.stream()
                .map((o) -> o.orderEvent.productId)
                .distinct()
                .collect(Collectors.toList());

        // Iterate through search events. For each, if any of its search result products
        // were included among the ordered products, move the search term from the non-attributed
        // group to the attributed group.
        for (Event search : input.searchEvents) {
            String searchTerm = search.searchEvent.searchTerm;

            if (processedSearchTerms.contains(searchTerm)) {
                continue;
            }

            // Iterate over search results.
            for (String searchResultProductId : search.searchEvent.searchResultProductIds) {
                if (orderedProductIds.contains(searchResultProductId)) {
                    nonAttributedSearchTerms.remove(searchTerm);
                    attributedSearchTerms.add(searchTerm);

                    // Process no more search results for this search event, and process
                    // no more searches that are for this search term, since the search term's
                    // attribution has already been determined.
                    processedSearchTerms.add(searchTerm);
                    break;
                }
            }
        }

        OrderSessionAttributions a = new OrderSessionAttributions();
        a.attributedSearchTerms = attributedSearchTerms;
        a.nonAttributedSearchTerms = nonAttributedSearchTerms;
        out.output(a);
    }
}
