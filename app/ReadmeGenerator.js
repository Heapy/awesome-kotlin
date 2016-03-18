const fs = require('./File');

const getLinks = () => {
    const data = require('./Kotlin.js');

    return data.map(category => {
        const subcategories = category.subcategories.map(subcategory => {
            const links = subcategory.links.map(link => {
                const getDesc = desc => desc ? `- ${desc}` : '';

                return `${link.href}[${link.name}^] ${getDesc(link.desc)}`;
            }).join('\n');

            return `=== ${subcategory.name}\n${links}\n`
        }).join('\n');

        return `== ${category.name}\n${subcategories}\n`
    }).join('\n');
};

const template = `
= Awesome Kotlin
:hardbreaks:
:toc:
:toc-placement!:

A curated list of awesome Kotlin related stuff Inspired by awesome-java.

image::https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg[Awesome, link="https://github.com/sindresorhus/awesome"]
image:https://api.travis-ci.org/JavaBy/awesome-kotlin.svg?branch=master["Build Status", link="https://travis-ci.org/JavaBy/awesome-kotlin"]

*Checkout our new site with search and repository stars:* http://kotlin.link/[http://kotlin.link/]

*RSS with information! Suggest links in issues (just create issue with link)* http://kotlin.link/rss.xml[RSS Link]

toc::[]

${getLinks()}

''''
NOTE: Get help with AsciiDoc syntax: http://asciidoctor.org/docs/asciidoc-writers-guide/[AsciiDoc Writer’s Guide]

image::https://licensebuttons.net/p/zero/1.0/80x15.png[CC0, link="http://creativecommons.org/publicdomain/zero/1.0/"]
`;

fs.write('./README.adoc', template);
