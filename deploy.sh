#!/bin/bash
set -e # exit with nonzero exit code if anything fails

# check current branch
if [ 'master' != $TRAVIS_BRANCH ]; then
    echo "Deploy only on master branch. Current branch: '$branch'.";
    exit 0;
fi

echo "Clear and re-create the dist directory...";
rm -rf dist || exit 0;

echo "Run kotlin application to generate various data...";
./gradlew installDist
AWESOME_KOTLIN_OPTS="-Xmx2g" ./build/install/awesome-kotlin/bin/awesome-kotlin true

echo "Build React Application...";
npm run pack

echo "Sync with remote folder..."
rsync -r --delete-after --quiet $TRAVIS_BUILD_DIR/dist/ deploy@kotlin.link:~/files/kotlin.link
