const sm = require('sitemap');
const fs = require('./File');
const articles = require("../app/rss/articles");

const sitemap = sm.createSitemap ({
    hostname: 'http://kotlin.link',
    cacheTime: 60*60*1000,
    urls: articles.map(article => ({ url: `/articles/${article.filename}` }))
});

var xml = sitemap.toString();

fs.write('./dist/sitemap.xml', xml);
