import {links} from './Kotlin';
import * as request from 'request';
import * as _ from 'lodash';

function HEAD(url: string): Promise<boolean> {
  return new Promise((resolve, reject) => {
    request({
      method: 'HEAD',
      url: encodeURI(url),
      headers: {
        'User-Agent': 'Awesome-Kotlin-List'
      }
    }, (error, response, body) => {
      if (!error && response.statusCode === 200) {
        resolve(false);
      } else {
        reject({
          url: url,
          error: error,
          status: response ? response.statusCode : '',
          body: body
        });
      }
    });
  });
}

const promises = _.flattenDeep<Promise<boolean>>(links.map(category => {
  return category.subcategories.map(subcategory => {
    return subcategory.links.map(link => {
      if (!link.whitelisted) {
        return HEAD(link.href).catch(error => {
          console.error(`Url '${link.href}' error: '${JSON.stringify(error)}'`);

          return !isWhiteListed(link.href, error.status);
        });
      } else {
        return Promise.resolve(false);
      }
    });
  });
}));

Promise.all(promises).then((statuses: boolean[]) => {
  console.log(`Link checking Done! Checked ${promises.length} links!`);

  if (statuses.some(it => it === true)) {
    process.exit(1);
  }
});

function isWhiteListed(url: string, status: number): boolean {
  // TODO: Externalize configuration
  return (url.includes('http://kotlin.link/articles/') && status === 404) ||
  (url === 'https://www.reddit.com/r/Kotlin/' && status === 503);
}
