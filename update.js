const request = require('request');
const fs = require('./app/File');
const json = require('./package.json');

const log = message => console.log(message); // eslint-disable-line

function checkIfUpdateAvailable(versions, curVersion, dependency) {
    const newVersion = Object.keys(versions).filter(ver => {
        if (['rc', 'beta', 'build', 'dev', 'b', 'alpha', 'pre'].some(it => ver.includes(it))) {
            return false;
        }

        const major = parseInt(ver.split('.')[0], 10);
        const minor = parseInt(ver.split('.')[1], 10);
        const path = parseInt(ver.split('.')[2], 10);

        const curMajor = parseInt(curVersion.split('.')[0], 10);
        const curMinor = parseInt(curVersion.split('.')[1], 10);
        const curPath = parseInt(curVersion.split('.')[2], 10);

        if ((major < curMajor)) {
            return false;
        } else if ((major <= curMajor) && (minor < curMinor)) {
            return false;
        } else if ((major <= curMajor) && (minor <= curMinor) && (path <= curPath)) {
            return false;
        }

        return true;
    });

    if (newVersion.length > 0) {
        log(`${dependency} should be updated to one of ${JSON.stringify(newVersion)}`);

        return newVersion[newVersion.length - 1];
    }
}

const check = dependencies => {
    return Object.keys(dependencies).map(dependency => {
        const currentVersion = dependencies[dependency].substring(1);

        return new Promise(resolve => {
            request(`https://registry.npmjs.com/${dependency}`, (error, response, body) => {
                if (!error && response.statusCode === 200) {
                    const data = JSON.parse(body);

                    // TODO: Sort A-Z, Show Category
                    const newVersion = checkIfUpdateAvailable(data.versions, currentVersion, dependency);

                    if (newVersion) {
                        dependencies[dependency] = `^${newVersion}`;
                    }

                    resolve();
                } else {
                    log(`Error while fetching data for package '${dependency}': ${JSON.stringify(error)}`);
                    resolve();
                }
            });
        });
    });
};

const devPromises = check(json.devDependencies);
const promises = check(json.dependencies);

Promise.all([...devPromises, ...promises]).then(() => {
    fs.write('./package.json', JSON.stringify(json, null, 2));
    log('Done.');
});
