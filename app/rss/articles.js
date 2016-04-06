const fm = require('front-matter');
const fs = require('fs');
const markdown = require('./markdown');
const articlesDir = fs.readdirSync('./app/rss/articles');

module.exports = articlesDir
    .filter(article => !article.startsWith('.'))
    .map(article => {
        const content = fs.readFileSync(`./app/rss/articles/${article}`, {encoding: 'UTF-8'});

        const data = fm(content);

        data.attributes.file = article;
        return data;
    })
    .map(article => {
        const attr =  article.attributes;

        if (!attr.title || !attr.url || !attr.categories || !attr.author || !attr.date) {
            throw new Error(`Metadata not complete: ${JSON.stringify(attr)}`);
        }

        return article;
    })
    .map(article => {
        article.attributes.description = markdown(article.body);
        return article.attributes;
    });
