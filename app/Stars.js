const request = require('request');
const fs = require('fs');

const getStarCount = repository => {
    var options = {
        url: `https://api.github.com/repos/${repository}`,
        headers: {
            'User-Agent': 'Awesome-Kotlin-List'
        }
    };

    return new Promise((resolve, reject) => {
        request(options, (error, response, body) => {
            if (!error && response.statusCode == 200) {
                var info = JSON.parse(body);
                resolve(info.stargazers_count);
            } else {
                reject(error);
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
    fs.writeFile("./Kotlin.json", JSON.stringify(data), function(err) {
        if(err) {
            return console.log(err);
        }

        console.log("The file was saved!");
    });
}, function(reason) {
    console.error(reason)
});
