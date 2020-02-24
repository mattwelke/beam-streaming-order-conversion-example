#!/bin/bash

OUTPUT_TABLE_SPEC="$PROJECT_ID:$OUTPUT_DATASET.$OUTPUT_TABLE"

mvn compile exec:java \
  -Dexec.mainClass=com.mattwelke.beamcustomaggexample.OrderConversion \
  -Dexec.cleanupDaemonThreads=false \
  -Dexec.args=" \
    --project=$PROJECT_ID \
    --runner=DataflowRunner \
    --experiments=enable_streaming_engine \
    --inputTopic=projects/$PROJECT_ID/topics/$INPUT_TOPIC \
    --outputTableSpec=$OUTPUT_TABLE_SPEC"
