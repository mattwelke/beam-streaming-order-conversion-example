module.exports = {
    delayMs: async (ms) => {
        return new Promise((accept, _) => {
            setTimeout(() => {
                accept();
            }, ms);
        })
    }
};
