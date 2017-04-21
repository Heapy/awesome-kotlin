#!/bin/bash
set -e # exit with nonzero exit code if anything fails

# check current branch
if [ 'master' != $TRAVIS_BRANCH ]; then
    echo "Deploy only on master branch. Current branch: '$branch'.";
    exit 0;
fi

# clear and re-create the dist directory
rm -rf dist || exit 0;

# Run kotlin application to generate various data
echo $JAVA_HOME
java -version
./gradlew --console plain --no-daemon --stacktrace run -Dtravis=true
# Build React Application
npm run pack

# sync with remote folder
rsync -r --delete-after --quiet $TRAVIS_BUILD_DIR/dist/ deploy@kotlin.link:~/files/kotlin.link
