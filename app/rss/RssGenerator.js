const fs = require('fs');
const RSS = require('rss');
const moment = require('moment');
const items = require('./Rss.js');

const feed = new RSS({
    title: 'Kotlin Programming Language',
    description: 'News, blog posts, projects, podcasts, videos and other. All information about Kotlin.',
    feed_url: 'https://javaby.github.io/awesome-kotlin/rss.xml',
    site_url: 'https://javaby.github.io/awesome-kotlin/',
    image_url: 'https://javaby.github.io/awesome-kotlin/favicon.ico',
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

items.default
    .sort((a, b) =>  {
        if (parseDate(a.date).isBefore(parseDate(b.date))) {
            return -1;
        } else if (parseDate(a.date).isAfter(parseDate(b.date))) {
            return 1;
        } else {
            return 0;
        }
    })
    .forEach(it => feed.item(it));


fs.writeFile("./dist/rss.xml", feed.xml(), error => {
    if (error) {
        console.log(`Error while writing file to fs: ${JSON.stringify(error)}`);
    }

    console.log("The file was saved!");
});
