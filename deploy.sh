#!/bin/bash
set -e # exit with nonzero exit code if anything fails

# clear and re-create the dist directory
rm -rf dist || exit 0;
mkdir dist;

# run our compile script
nvm install 5
npm run ci

# go to the dist directory and create a *new* Git repo
cd dist
git init

# inside this git repo we'll pretend to be a new user
git config user.name "Travis CI"
git config user.email "ruslan@ibragimov.by"

# The first and only commit to this new Git repo contains all the
# files present with the commit message "Deploy to GitHub Pages".
git add .
git commit -m "Deploy to GitHub Pages"

# Force push from the current repo's master branch to the remote
# repo's gh-pages branch. (All previous history on the gh-pages branch
# will be lost, since we are overwriting it.) We redirect any output to
# /dev/null to hide any sensitive credential data that might otherwise be exposed.
git push --force --quiet "https://${GH_TOKEN}@github.com/KotlinBy/awesome-kotlin.git" master:gh-pages > /dev/null 2>&1
