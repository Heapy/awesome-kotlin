const fs = require('fs');
const remark = require('remark');
const html = require('remark-html');
const yamlConfig = require('remark-yaml-config');

const processor = remark().use(yamlConfig).use(html);
const config =
`---
remark:
  commonmark: true
---
`;

exports.default = function (file) {
    const content = fs.readFileSync(`./app/rss/articles/${file}`, {encoding: 'UTF-8'});


    return processor.process(`${config}\n${content}`);
};