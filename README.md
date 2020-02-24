# beam-streaming-order-conversion-example

Example using Apache Beam to perform streaming session conversion calculations. After 5 seconds of inactivity, a session is emitted further in the Beam pipeline. An example `event-emitter` Node.js program can be used to mimic sessions with a few different search terms, some of them converting and some not.

Set up to run on GCP Dataflow by default.

## Setting up GCP for Example

In each of these steps, when an env var is mentioned, set it in a terminal you will use in the step below to run the example. Each of these steps can be completed via the GCP web console. You must have the `gcloud` CLI installed before continuing.

1. Set the env var `PROJECT_ID` to your GCP project.
1. Create a new table in BQ in a new dataset with the schema in `bq_table_schema.json`. Set the env var `OUTPUT_DATASET` to the name of the dataset you created, and set the env var `OUTPUT_TABLE` to the name of the table you created.
1. Create a Pub/Sub topic to emit the search and order events to. Default settings are fine. Set the env var `INPUT_TOPIC` to the name of the topic you created.
1. Use the command `gcloud auth application-default login` to log in to GCP and get a keyfile saved to your home directory that `gcloud` will use to deploy the Dataflow streaming job.
1. Create a service account for the `event-emitter` Node.js program. Give it the `Pub/Sub Publisher` role. Save a key file for the service account to your local machine. Set the env var `GOOGLE_APPLICATION_CREDENTIALS` to the path on your machine where you saved this key file.

## Running Example

These steps must be performed in the terminal you set the env vars from the steps above in.

1. To start the Dataflow job, run `start.sh` in the `order-conversion` directory. When the console says "Workers started successfully", hit `ctrl-c` to regain control of your terminal.
1. To start the event emitter program, run `node index.js` in the `event-emitter` directory. This will be a long running program that sends 1000 sessions. Use `ctrl-c` to stop it after about 30 seconds which is enough time for a few sessions to have been sent.
1. Run `query.sh` to run a BigQuery query job via the `bq` CLI tool and see the conversion ratio measured so far. Note that there may be a minute or two of system lag in the Dataflow job before you begin to see results.
