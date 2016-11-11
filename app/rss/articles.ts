import * as moment from 'moment';

const fm = require('front-matter');
const fs = require('fs');
const markdown = require('./markdown');
const articlesDir = fs.readdirSync('./app/rss/articles');

articlesDir.forEach(article => validateArticle(article));

const parseDate = date => moment(date, 'MMM DD, YYYY hh:mm');

const sortByDate = (a, b) => {
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
    .split('') // split string on array of symbols
    .map(it => it.charCodeAt(0)) // convert string in corresponding codes
    .map(code => { // replace all special symbols with dash
      // See html codes: 32 - space, 47 - slash, 58 - colon, 64 - at, 91 - opening bracket, 96 - grave accent
      // http://ascii-code.com/
      if ((code > 31 && code < 48) ||
        (code > 57 && code < 65) ||
        (code > 90 && code < 97) ||
        (code > 122 && code < 256)) {
        return '-';
      } else {
        return String.fromCharCode(code); // return symbol instead of his code
      }
    })
    .join('')
    .replace(new RegExp('-$', 'g'), '') // Replace last dash in string with ''
    .replace(new RegExp('^-', 'g'), '') // Replace first dash in string with ''
    .replace(new RegExp('-+', 'g'), '-'); // Replace multiple dashes with one dash

  return `${escaped}.html`
};

export const articles: Article[] = articlesDir
  .filter(article => !article.startsWith('.'))
  .map(article => {
    const content = fs.readFileSync(`./app/rss/articles/${article}`, {encoding: 'UTF-8'});

    const data = fm(content);

    data.attributes.file = article;
    return data;
  })
  .map(article => {
    const attr = article.attributes;

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

function validateArticle(name: string) {
  const invalid = ['\\', '/', ':', '*', '?', '"', '<', '>', '|'];
  const includes = invalid.find(symbol => name.includes(symbol));

  if (includes) {
    throw new Error(`File '${name}' includes restricted symbol '${includes}'.`);
  }
}
