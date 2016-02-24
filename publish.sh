#!/bin/bash

if [ $# -eq 0 ]; then
  echo 'Please, pass commit message: ./publish.sh "Fix issue #1"';
  exit 1;
fi

npm run build

git add README.adoc app/Rss.js app/Kotlin.js
git commit -m "$1"
git push

git checkout gh-pages
git pull
cp dist/rss.xml .
cp dist/bundle.js .
cp dist/index.html .
git add rss.xml bundle.js index.html
git commit -m "$1"
git push

git checkout master

echo "All done!"
