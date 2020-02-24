
const { delayMs } = require('./utils');

module.exports = async (topic, visId) => {
    const search = {
        eventType: 'search',
        visitorId: visId,
        searchEvent: {
            searchTerm: 'apple',
            searchResultProductIds: [
                'one apple (fruit)',
                'Apple MacBook',
            ],
        },
    };
    await topic.publish(Buffer.from(JSON.stringify(search)));
    console.info(`Sent search:`, JSON.stringify(search, null, 4));

    await delayMs(2500);

    const order = {
        eventType: 'order',
        visitorId: visId,
        orderEvent: {
            productId: 'one apple (fruit)',
        },
    };
    await topic.publish(Buffer.from(JSON.stringify(order)));
    console.info(`Sent order:`, JSON.stringify(order, null, 4));
};
