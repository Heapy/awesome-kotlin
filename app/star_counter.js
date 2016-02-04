const request = require('request');
const getStarCount = repository => {
    var options = {
        url: `https://api.github.com/repos/JavaBy/awesome-kotlin`,
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

const data = require('./Kotlin.js')
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

getStarCount('JavaBy/awesome-kotlin').then(data => {
    console.log(data);
})


Promise.all(promises).then(() => {
    console.log('done');
}, function(reason) {
    console.log(reason)
});
