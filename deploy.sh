#!/bin/bash
set -e # exit with nonzero exit code if anything fails

echo "TRAVIS_BRANCH=${TRAVIS_BRANCH}"
echo "TRAVIS_BUILD_DIR=${TRAVIS_BUILD_DIR}"
echo "TRAVIS_PULL_REQUEST=${TRAVIS_PULL_REQUEST}"

# If current branch not "master" or it's PR
if [ "${TRAVIS_BRANCH}" = "master" ] && [ "${TRAVIS_PULL_REQUEST}" = "false" ]; then
    echo "Clear and re-create the dist directory...";
    rm -rf dist || exit 1;

    echo "Run kotlin application to generate various data...";
    ./gradlew installDist
    AWESOME_KOTLIN_OPTS="-Xmx2g" ./build/install/awesome-kotlin/bin/awesome-kotlin true

    echo "Build React Application...";
    npm run pack

    echo "Sync with remote folder..."
    rsync -r --delete-after --quiet "${TRAVIS_BUILD_DIR}/dist/" deploy@kotlin.link:~/files/kotlin.link
else
  echo "Deploy only on master branch. Current branch: \"${TRAVIS_BRANCH}\".";
  exit 0;
fi
