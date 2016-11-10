import {write} from './File';
import {articles} from './rss/articles';
const sm = require('sitemap');

const sitemap = sm.createSitemap({
  hostname: 'http://kotlin.link',
  cacheTime: 60 * 60 * 1000,
  urls: articles.map(article => ({url: `/articles/${article.filename}`}))
});

const xml = sitemap.toString();

write('./dist/sitemap.xml', xml);
