const fs = require('fs');

module.exports = {
    write(file, data) {
        return new Promise((resolve, reject) => {
            fs.writeFile(file, data, error => {
                if (error) {
                    console.log(`Error while writing file '${file}' to fs: ${JSON.stringify(error)}`);
                    reject(error);
                }

                console.log(`The file '${file}' was saved!`);
                resolve(file);
            });
        });
    },
    readSync(file) {
        return fs.readFileSync(file, {encoding: 'UTF-8'})
    }
};