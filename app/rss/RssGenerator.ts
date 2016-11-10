const file = require('../File');
const RSS = require('rss');

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

const articles = require('./articles')
    .forEach(it => feed.item(it));

file.write(`./dist/rss.xml`, feed.xml());
