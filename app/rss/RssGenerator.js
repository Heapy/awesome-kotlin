const fs = require('fs');
const file = require('../File');
const RSS = require('rss');
const moment = require('moment');
const fm = require('front-matter');
const markdown = require('./markdown');

const feed = new RSS({
    title: 'Kotlin Programming Language',
    description: 'News, blog posts, projects, podcasts, videos and other. All information about Kotlin.',
    feed_url: 'http://kotlin.link/rss.xml',
    site_url: 'http://kotlin.link/',
    image_url: 'http://kotlin.link/favicon.ico',
    docs: 'https://validator.w3.org/feed/docs/rss2.html',
    managingEditor: 'ruslan@ibragimov.by (Ruslan Ibragimov)',
    webMaster: 'ruslan@ibragimov.by (Ruslan Ibragimov)',
    copyright: 'CC0 1.0 Universal (CC0 1.0)',
    language: 'en',
    categories: ['Kotlin','Programming','JVM'],
    pubDate: new Date().toUTCString(),
    ttl: '30',
    generator: 'node-rss'
});

const parseDate = date => moment(date, 'MMM DD, YYYY');

const articlesDir = fs.readdirSync('./app/rss/articles');

const sortByDate = (a, b) =>  {
    if (parseDate(a.date).isBefore(parseDate(b.date))) {
        return 1;
    } else if (parseDate(a.date).isAfter(parseDate(b.date))) {
        return -1;
    } else {
        return 0;
    }
};

const articles = articlesDir
    .filter(article => !article.startsWith('.'))
    .filter(article => article !== 'README.md')
    .map(article => {
        console.log(article);
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
    })
    .sort(sortByDate);

articles.forEach(it => feed.item(it));

const links = articles.map(it => `* ${parseDate(it.date).format('MMM DD, YYYY')} [${it.title}](./${encodeURI(it.file)})`);
const readme = `# Articles\n${links.join('\n')}`;

file.write(`./dist/rss.xml`, feed.xml());
file.write('./app/rss/articles/README.md', readme);
