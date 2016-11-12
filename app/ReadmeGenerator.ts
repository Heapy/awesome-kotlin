import {write} from './File';
import {links} from './Kotlin';
import {getUID} from './Utils';

function addIdsToLinks(categrories: Category[]) {
  return categrories.map(categrory => {
    const id = getUID();
    const subcategories = categrory.subcategories.map(subcategory => Object.assign({}, subcategory, {id: getUID()}));
    return Object.assign({}, categrory, {id, subcategories});
  });
}

const linksWithIds = addIdsToLinks(links);

write('./README.md', `# Awesome Kotlin ([https://kotlin.link](https://kotlin.link))

A curated list of awesome Kotlin related stuff inspired by awesome-java. :octocat:

[![Awesome](https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg)](https://github.com/sindresorhus/awesome) [![Build Status](https://api.travis-ci.org/KotlinBy/awesome-kotlin.svg?branch=master)](https://travis-ci.org/KotlinBy/awesome-kotlin)

:newspaper: [RSS Feed of articles, videos, slides, updates (20 latest articles)](http://kotlin.link/rss.xml)

:newspaper: [RSS Feed of articles, videos, slides, updates (full archive)](http://kotlin.link/rss-full.xml)

## Spread Awesome Kotlin!

Here awesome badge for your project:

\`\`\`markdown
[![Awesome Kotlin Badge](https://github.com/KotlinBy/awesome-kotlin/raw/master/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin)
\`\`\`

## Table of Contents

${tableOfContent()}

${getLinks()}

---

[![CC0](https://licensebuttons.net/p/zero/1.0/80x15.png)](http://creativecommons.org/publicdomain/zero/1.0/)
`);

function getLinks() {
  return linksWithIds.map(category => {
    const subcategories = category.subcategories.map(subcategory => {
      const links = subcategory.links.map(link => {
        const getDesc = desc => desc ? `- ${desc}` : '';

        return `* [${link.name}](${link.href}) ${getDesc(link.desc)}`;
      }).join('\n');

      return `### ${getAnchor(subcategory.id)}${subcategory.name} <sup>[Back ⇈](#${subcategory.id}-subcategory)</sup>\n${links}\n`
    }).join('\n');

    return `## ${getAnchor(category.id)}${category.name} <sup>[Back ⇈](#${category.id}-category)</sup>\n${subcategories}\n`
  }).join('\n');
}

function tableOfContent() {
  function getSubcategories(category: Category) {
    return category
      .subcategories
      .map(subcategory => {
        return `* ${getAnchor(subcategory.id + "-subcategory")}[${subcategory.name}](#${subcategory.id})`;
      })
      .join('\n');
  }

  return linksWithIds
    .map(category => {
      return `### ${getAnchor(category.id + "-category")}[${category.name}](#${category.id})\n${getSubcategories(category)}`;
    })
    .join('\n\n');
}


function getAnchor(name: string): string {
  return `<a name="${name}"></a>`
}
