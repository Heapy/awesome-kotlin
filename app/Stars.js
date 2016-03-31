const request = require('request');
const fs = require('./File');

const user = process.env.GH_USER;
const pass = process.env.GH_TOKEN;

if (!user || !pass) {
    throw new Error(`You should run this script only when you added GH_USER and GH_TOKEN to env. 
Token can be found here: https://github.com/settings/tokens`);
}

const getStarCount = repository => {
    var options = {
        url: `https://api.github.com/repos/${repository}`,
        headers: {
            'User-Agent': 'Awesome-Kotlin-List'
        },
        auth: {user, pass}
    };

    return new Promise((resolve, reject) => {
        request(options, (error, response, body) => {
            if (!error && response.statusCode == 200) {
                var info = JSON.parse(body);
                resolve(info.stargazers_count);
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

const data = require('./Kotlin.js');
const promises = [];

data.forEach(category => {
    category.subcategories.forEach(subcategory => {
        subcategory.links.forEach(link => {
            if (link.type === 'github') {
                promises.push(getStarCount(link.name).then(stars => {
                    link.star = stars;
                }));
            }
        });
    });
});

Promise.all(promises).then(() => {
    fs.write("./app/Kotlin.json", JSON.stringify(data));
}, reason => {
    console.error(`Error while stars getting ${JSON.stringify(reason)}`);
});
