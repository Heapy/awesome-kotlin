const request = require('request');
const _ = require('lodash');

const HEAD = url => {
    return new Promise((resolve, reject) => {
        request({
            method: 'HEAD',
            url: encodeURI(url),
            headers: {
                'User-Agent': 'Awesome-Kotlin-List'
            }
        }, (error, response, body) => {
            if (!error && response.statusCode == 200) {
                resolve(url);
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
};

const data = require('./Kotlin.js');
const promises = _.flattenDeep(data.map(category => {
    return category.subcategories.map(subcategory => {
        return subcategory.links.map(link => {
            if (!link.whitelisted) {
                return HEAD(link.href).catch(error => {
                    console.error(`Url '${link.href}' error: '${JSON.stringify(error)}'`);
                    return true;
                });
            } else {
                return Promise.resolve(false);
            }
        });
    });
}));

Promise.all(promises).then(statuses => {
    console.log(`Link checking Done! Checked ${promises.length} links!`);

    if (statuses.some(it => it === true)) {
        process.exit(1);
    }
});
