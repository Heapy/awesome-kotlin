const fs = require('./File');
const data = require('./Kotlin.js');

const links = () => {
  return data.map(category => {
    const subcategories = category.subcategories.map(subcategory => {
      const links = subcategory.links.map(link => {
        const getDesc = desc => desc ? `- ${desc}` : '';

        return `* [${link.href}](${link.name}) ${getDesc(link.desc)}`;
      }).join('\n');

      return `### ${subcategory.name}\n${links}\n`
    }).join('\n');

    return `## ${category.name}\n${subcategories}\n`
  }).join('\n');
};

const tableOfContent = () => {
  function generateTOC() {
    function getCategoryUrl(name) {
      return name.toLowerCase().replace(new RegExp(' ', 'g'), '-');
    }

    function getSubcategories(category) {
      return category
        .subcategories
        .map(subcategory => {
          return `* [${subcategory.name}](#${getCategoryUrl(subcategory.name)})`;
        })
        .join('\n');
    }

    return data
      .map(category => {
        return `### [${category.name}](#${getCategoryUrl(category.name)})\n${getSubcategories(category)}`;
      })
      .join('\n\n');
  }

  return ['## Table of Contents', generateTOC()].join('\n');
};

const template = `# Awesome Kotlin ([https://kotlin.link](https://kotlin.link))

A curated list of awesome Kotlin related stuff Inspired by awesome-java.

[![Awesome](https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg)](https://github.com/sindresorhus/awesome) [![Build Status](https://api.travis-ci.org/KotlinBy/awesome-kotlin.svg?branch=master)](https://travis-ci.org/KotlinBy/awesome-kotlin) 

[RSS Feed of articles, videos, slides, updates](http://kotlin.link/rss.xml)

${tableOfContent()}

${links()}

---

[![CC0](https://licensebuttons.net/p/zero/1.0/80x15.png)](http://creativecommons.org/publicdomain/zero/1.0/)
`;

fs.write('./README.md', template);
