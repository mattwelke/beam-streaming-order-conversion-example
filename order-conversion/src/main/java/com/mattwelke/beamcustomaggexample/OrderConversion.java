package com.mattwelke.beamcustomaggexample;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.options.StreamingOptions;
import org.apache.beam.sdk.options.Validation.Required;
import org.apache.beam.sdk.transforms.Combine;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.windowing.Sessions;
import org.apache.beam.sdk.transforms.windowing.Window;
import org.joda.time.Duration;

/**
 * A pipeline that accepts messages from Pub/Sub and writes then to BQ after
 * processing.
 */
public class OrderConversion {
    public interface PubSubToBQOptions extends PipelineOptions, StreamingOptions {
        @Description("The Pub/Sub topic to read from.")
        @Required
        String getInputTopic();
        void setInputTopic(String value);

        @Description("Tablespec of the BigQuery table to write to.")
        @Required
        String getOutputTableSpec();
        void setOutputTableSpec(String value);
    }

    public static void main(String[] args) {
        PubSubToBQOptions options = PipelineOptionsFactory
                .fromArgs(args)
                .withValidation()
                .as(PubSubToBQOptions.class);

        options.setStreaming(true);

        Pipeline pipeline = Pipeline.create(options);

        pipeline
                .apply("Read Pub/Sub Messages", PubsubIO.readStrings().fromTopic(options.getInputTopic()))
                .apply("Parse Events", ParDo.of(new ParseEventsDoFn()))
                .apply("Keep Only Search & Order Events", ParDo.of(new KeepOnlySearchAndOrderDoFn()))
                .apply("Add Visitor ID Key", ParDo.of(new ToVisitorIdKeyDoFn()))
                .apply("Session Window", Window.into(Sessions.withGapDuration(Duration.standardSeconds(5))))
                .apply("CombinePerKey", Combine.perKey(new GroupIntoOrderSessionCombineFn()))
                .apply("Remove Key", ParDo.of(new FromVisitorIdKeyDoFn()))
                .apply("Calculate Order Attribution", ParDo.of(new CalcOrderAttributionsDoFn()))
                .apply("Transform to BigQuery Row", ParDo.of(new OrderSessionAttributionsToBQRowDoFn()))
                .apply("Write to BigQuery", BigQueryIO
                        .writeTableRows()
                        .to(options.getOutputTableSpec())
                        .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_NEVER)
                        .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND)
                );

        pipeline.run().waitUntilFinish();
    }
}
