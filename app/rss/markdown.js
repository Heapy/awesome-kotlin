const remark = require('remark');
const html = require('remark-html');

const processor = remark().use(html);

module.exports = function (content) {
    return processor.process(content, {
        commonmark: true
    });
};
