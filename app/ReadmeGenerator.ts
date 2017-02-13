import {write} from './File';
import {links} from './Kotlin';

write('./README.md', `# Awesome Kotlin ([https://kotlin.link](https://kotlin.link))

A curated list of awesome Kotlin related stuff inspired by awesome-java. :octocat:

[![List of Awesome List Badge](https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg)](https://github.com/sindresorhus/awesome) [![Travis CI Build Status Badge](https://api.travis-ci.org/KotlinBy/awesome-kotlin.svg?branch=master)](https://travis-ci.org/KotlinBy/awesome-kotlin) [![Awesome Kotlin Badge](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin)

:newspaper: [RSS Feed of articles, videos, slides, updates (20 latest articles)](http://kotlin.link/rss.xml)

:newspaper: [RSS Feed of articles, videos, slides, updates (full archive)](http://kotlin.link/rss-full.xml)

## Spread Awesome Kotlin!

Here awesome badge for your project:

\`\`\`markdown
[![Awesome Kotlin Badge](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin)
\`\`\`

## Table of Contents

${tableOfContent()}

${getLinks()}

---

[![CC0](https://licensebuttons.net/p/zero/1.0/80x15.png)](http://creativecommons.org/publicdomain/zero/1.0/)
`);

function getLinks() {
  return links.map(category => {
    const subcategories = category.subcategories.map(subcategory => {
      const links = subcategory.links.map(link => {
        const getDesc = desc => desc ? `- ${desc}` : '';

        return `* [${link.name}](${link.href}) ${getDesc(link.desc)}`;
      }).join('\n');

      return `${getSubcategoryName(subcategory.name, category.name)}\n${links}\n`;
    }).join('\n');

    return `${getCategoryName(category.name)}\n${subcategories}\n`;
  }).join('\n');
}

function tableOfContent() {
  function getSubcategories(category: Category) {
    return category
      .subcategories
      .map(subcategory => getTocSubcategoryName(subcategory.name, category.name))
      .join('\n');
  }

  return links
    .map(category => getTocCategoryName(category.name) + '\n' + getSubcategories(category))
    .join('\n\n');
}

function getAnchor(name: string): string {
  return `<a name="${normalizeName(name)}"></a>`
}

function getCategoryName(name: string) {
  return `## ${getAnchor(name)}${name} <sup>${link('Back ⇈', name + '-category')}</sup>`;
}

function getSubcategoryName(name: string, namespace: string) {
  return `### ${getAnchor(namespace + '-' + name)}${name} <sup>${link('Back ⇈', namespace + '-' + name + '-subcategory')}</sup>`;
}

function getTocCategoryName(name: string) {
  return `### ${getAnchor(name + "-category")}${link(name, name)}`;
}

function getTocSubcategoryName(name: string, namespace: string) {
  return `* ${getAnchor(namespace + '-' + name + "-subcategory")}${link(name, namespace + '-' + name)}`;
}

function normalizeName(name: string): string {
  return name
    .toLowerCase()
    .replace(new RegExp('[ ,\\/]', 'g'), '-') // Replace special symbols with dash
    .replace(new RegExp('-+', 'g'), '-'); // Replace multiple dashes with one dash
}

function link(name: string, url: string) {
  return `[${name}](#${normalizeName(url)})`;
}
