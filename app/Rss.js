var fs = require('fs');
var RSS = require('rss');

var feed = new RSS({
    title: 'Kotlin Programming Language.',
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


fs.writeFile("./dist/rss.xml", feed.xml(), error => {
    if (error) {
        console.log(`Error while writing file to fs: ${JSON.stringify(error)}`);
    }

    console.log("The file was saved!");
});
