const remark = require('remark');
const html = require('remark-html');
const hljs  = require('remark-highlight.js');

const processor = remark().use(html).use(hljs);

module.exports = function (content) {
    return processor.process(content, {
        commonmark: true
    });
};
