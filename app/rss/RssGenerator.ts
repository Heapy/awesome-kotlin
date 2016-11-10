import {writeSync} from '../File';
import {articles} from './articles';
import * as RSS from 'rss';
import * as _ from 'lodash';

generate('rss.xml', articles, 20);
generate('rss-full.xml', articles);

function generate(name: string, articles: Article[], limit: number = articles.length) {
  const feed = new RSS({
    title: 'Kotlin Programming Language',
    description: 'News, blog posts, projects, podcasts, videos and other. All information about Kotlin.',
    feed_url: `http://kotlin.link/${name}`,
    site_url: 'http://kotlin.link/',
    image_url: 'http://kotlin.link/favicon.ico',
    docs: 'https://validator.w3.org/feed/docs/rss2.html',
    managingEditor: 'ruslan@ibragimov.by (Ruslan Ibragimov)',
    webMaster: 'ruslan@ibragimov.by (Ruslan Ibragimov)',
    copyright: 'CC0 1.0 Universal (CC0 1.0)',
    language: 'en',
    categories: ['Kotlin', 'Programming', 'JVM'],
    pubDate: new Date().toUTCString(),
    ttl: 30,
    generator: 'node-rss'
  });

  _.take(articles, limit).forEach(article => feed.item(article));

  writeSync(`./dist/${name}`, feed.xml());
}
