
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
};
