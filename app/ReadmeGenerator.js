const fs = require('fs');

const getLinks = () => {
    const data = require('./Kotlin.js').default;

    return data.map(category => {
        const subcategories = category.subcategories.map(subcategory => {
            const links = subcategory.links.map(link => {
                const getDesc = desc => desc ? `- ${desc}` : '';

                return `${link.href}[${link.name}] ${getDesc(link.desc)}`;
            }).join('\n');

            return `=== ${subcategory.name}\n${links}\n`
        }).join('\n');

        return `== ${category.name}\n${subcategories}\n`
    }).join('\n');
};

const template = `
= Awesome Kotlin
:hardbreaks:

A curated list of awesome Kotlin related stuff Inspired by awesome-java.

image::https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg[Awesome, link="https://github.com/sindresorhus/awesome"]

*Checkout our new site with search and repository stars:* https://javaby.github.io/awesome-kotlin/[https://javaby.github.io/awesome-kotlin/]

${getLinks()}

''''
NOTE: Get help with AsciiDoc syntax: http://asciidoctor.org/docs/asciidoc-writers-guide/[AsciiDoc Writer’s Guide]

image::http://i.creativecommons.org/p/zero/1.0/80x15.png[CC0, link="http://creativecommons.org/publicdomain/zero/1.0/"]
`;

fs.writeFile("./README.adoc", template, error => {
    if (error) {
        console.log(`Error while writing file to fs: ${JSON.stringify(error)}`);
    }

    console.log("The file was saved!");
});
