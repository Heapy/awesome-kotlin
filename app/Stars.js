const request = require('request');
const _ = require('lodash');
const fs = require('./File');

const user = process.env.GH_USER;
const pass = process.env.GH_TOKEN;

if (!user || !pass) {
    throw new Error(`You should run this script only when you added GH_USER and GH_TOKEN to env. 
Token can be found here: https://github.com/settings/tokens`);
}

const JSON_GET = (options, repository, handler) => {
    return new Promise((resolve, reject) => {
        request(options, (error, response, body) => {
            if (!error && response.statusCode == 200) {
                var info = JSON.parse(body);
                resolve(handler(info));
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
};

const getGithubStarCount = repository => {
    var options = {
        url: `https://api.github.com/repos/${repository}`,
        headers: {
            'User-Agent': 'Awesome-Kotlin-List'
        },
        auth: {user, pass}
    };

    return JSON_GET(options, repository, json => json.stargazers_count);
};

const getBitbucketWatcherCount = repository => {
    var options = {
        url: `https://api.bitbucket.org/2.0/repositories/${repository}/watchers`,
        headers: {
            'User-Agent': 'Awesome-Kotlin-List'
        }
    };

    return JSON_GET(options, repository, json => json.size);
};

const data = require('./Kotlin.js');
const promises = _.flattenDeep(data.forEach(category => {
    return category.subcategories.forEach(subcategory => {
        return subcategory.links.forEach(link => {
            if (link.type === 'github') {
                return getGithubStarCount(link.name).then(stars => {
                    link.star = stars;
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
    fs.write("./app/Kotlin.json", JSON.stringify(data));
}, reason => {
    console.error(`Error while stars getting ${JSON.stringify(reason)}`);
    process.exit(1);
});
