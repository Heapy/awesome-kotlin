const fm = require('front-matter');
const fs = require('fs');
const markdown = require('./markdown');
const articlesDir = fs.readdirSync('./app/rss/articles');
const moment = require('moment');

const parseDate = date => moment(date, 'MMM DD, YYYY');

const sortByDate = (a, b) =>  {
    if (parseDate(a.date).isBefore(parseDate(b.date))) {
        return 1;
    } else if (parseDate(a.date).isAfter(parseDate(b.date))) {
        return -1;
    } else {
        return 0;
    }
};

const getFileName = name => {
    const escaped = name
        .replace(new RegExp('[/ :!,\'"`+#)(-]', 'g'), '_')
        .replace(new RegExp('_+', 'g'), '_')
        .replace(new RegExp('\\.', 'g'), '');

    return `${escaped}.html`
};

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
        article.attributes.filename = getFileName(article.attributes.title);
        return article;
    })
    .map(article => {
        article.attributes.description = markdown(article.body);
        return article.attributes;
    })
    .sort(sortByDate);
