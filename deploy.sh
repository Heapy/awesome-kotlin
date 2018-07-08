#!/bin/bash
set -e # exit with nonzero exit code if anything fails

echo "TRAVIS_BUILD_DIR=${TRAVIS_BUILD_DIR}"

echo "Clear and re-create the dist directory...";
rm -rf dist || exit 1;

echo "Run kotlin application to generate various data...";
AWESOME_KOTLIN_OPTS="-Xmx2g" ./build/install/awesome-kotlin/bin/awesome-kotlin true

echo "Build React Application...";
npm run pack

echo "Add ssh key to remote server..."
openssl aes-256-cbc -K $encrypted_83630750896a_key -iv $encrypted_83630750896a_iv -in deploy@morty.enc -out ~/.ssh/deploy@morty -d
chmod 600 ~/.ssh/deploy@morty
eval "$(ssh-agent -s)"
ssh-add ~/.ssh/deploy@morty

echo "Sync with remote folder..."
rsync -r --delete-after --quiet "${TRAVIS_BUILD_DIR}/dist/" deploy@kotlin.link:~/files/kotlin.link
