import fs = require('fs');

export function write(file, data) {
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
}

export function writeSync(file, data) {
  try {
    fs.writeFileSync(file, data);
    console.log(`The file '${file}' was saved!`);
  } catch (exception) {
    console.log(`Error while writing file '${file}' to fs: ${JSON.stringify(exception)}`);
  }
}

export function readSync(file) {
  return fs.readFileSync(file, {encoding: 'UTF-8'})
}
