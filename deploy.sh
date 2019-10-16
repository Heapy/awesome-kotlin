#!/bin/bash
set -e # exit with nonzero exit code if anything fails

echo "Add ssh key to remote server..."
openssl aes-256-cbc -K $encrypted_83630750896a_key -iv $encrypted_83630750896a_iv -in deploy@morty.enc -out ~/.ssh/deploy@morty -d
chmod 600 ~/.ssh/deploy@morty
eval "$(ssh-agent -s)"
ssh-add ~/.ssh/deploy@morty
