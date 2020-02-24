const { PubSub } = require('@google-cloud/pubsub');

const session1 = require('./session1');
const session2 = require('./session2');
const session3 = require('./session3');
const { delayMs } = require('./utils');

async function main() {
    const pubsub = new PubSub({
        projectId: process.env['PROJECT_ID'],
    });

    const topic = pubsub.topic(process.env['INPUT_TOPIC']);

    for (let i = 1; i <= 1000; i++) {
        await delayMs(1000);
        session1(topic, `a${i}`);

        await delayMs(1000);
        session2(topic, `b${i}`);

        await delayMs(1000);
        session3(topic, `c${i}`);
    }
}

main().catch(e => {
    console.error(e.message);
    throw e;
});
