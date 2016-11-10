import * as moment from 'moment';
import * as _ from 'lodash';
import * as request from 'request';
import {write} from './File';
import {links} from './Kotlin';

const user: string = process.env.GH_USER;
const pass: string = process.env.GH_TOKEN;

if (!user || !pass) {
  throw new Error(`You should run this script only when you added GH_USER and GH_TOKEN to env. 
Token can be found here: https://github.com/settings/tokens`);
}

function JSON_GET(options, repository, handler) {
  return new Promise((resolve, reject) => {
    request(options, (error, response, body) => {
      if (!error && response.statusCode == 200) {
        const data = JSON.parse(body);
        resolve(handler(data));
      } else {
        reject({
          repository: repository,
          error: error,
          status: response ? response.statusCode : '',
          body: body
        });
      }
    });
  });
}

interface LinkExtraData {
  readonly stars: number;
  readonly update: string;
}

function getGithubStarCount(repository: string): Promise<LinkExtraData> {
  const options = {
    url: `https://api.github.com/repos/${repository}`,
    headers: {
      'User-Agent': 'Awesome-Kotlin-List'
    },
    auth: {user, pass}
  };

  return JSON_GET(options, repository, json => ({
    stars: json.stargazers_count,
    update: json.pushed_at
  }));
}

const getBitbucketWatcherCount = repository => {
  const options = {
    url: `https://api.bitbucket.org/2.0/repositories/${repository}/watchers`,
    headers: {
      'User-Agent': 'Awesome-Kotlin-List'
    }
  };

  return JSON_GET(options, repository, json => json.size);
};

const promises = _.flattenDeep(links.map(category => {
  return category.subcategories.map(subcategory => {
    return subcategory.links.map(link => {
      if (link.type === 'github') {
        return getGithubStarCount(link.name).then(data => {
          link.star = data.stars;
          link.update = moment(data.update).format('MMM DD, YYYY');
        });
      } else if (link.type === 'bitbucket') {
        return getBitbucketWatcherCount(link.name).then(watchers => {
          link.star = watchers;
        });
      } else {
        return Promise.resolve();
      }
    });
  });
}));

Promise.all(promises).then(() => {
  write("./app/LinksWithStars.json", JSON.stringify(links));
}, reason => {
  console.error(`Error while stars getting ${JSON.stringify(reason)}`);
  process.exit(1);
});
