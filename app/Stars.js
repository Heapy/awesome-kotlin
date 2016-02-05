const request = require('request');
const fs = require('fs');

const getStarCount = repository => {
    var options = {
        url: `https://api.github.com/repos/${repository}`,
        headers: {
            'User-Agent': 'Awesome-Kotlin-List'
        },
        'auth': {
            'user': 'IRus',
            'pass': 'https://github.com/settings/tokens'
        }
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
                    status: response.statusCode,
                    body: body
                });
            }
        });
    });
};

const data = require('./Kotlin.js').default;
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
    fs.writeFile("./app/Kotlin.json", JSON.stringify(data), error => {
        if (error) {
            console.log(`Error while writing file to fs: ${JSON.stringify(error)}`);
        }

        console.log("The file was saved!");
    });
}, reason => {
    console.error(`Error while stars getting ${JSON.stringify(reason)}`);
});
