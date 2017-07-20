const fm = require('front-matter');
const fs = require('fs');
const moment = require('moment');

const parseDate = date => moment(date, 'MMM DD, YYYY hh:mm');

const folder = './articles/english/2016/';

const articlesDir = fs.readdirSync(folder);

articlesDir
    .filter(article => article.endsWith(".md"))
    .forEach(it => {
        fs.unlinkSync(`${folder}${it}`);
    });

articlesDir
    .filter(article => article.endsWith(".md"))
    .map(article => {
        const content = fs.readFileSync(`${folder}${article}`, {encoding: 'UTF-8'});
        const data = fm(content);

        data.file = article;

        data.kts = getScript(data);

        return data;
    })
    .forEach(it => {
        fs.writeFileSync(`${folder}${it.file.replace(".md", ".kts")}`, it.kts, {encoding: 'UTF-8'});
    });


function getCategories(list) {
    return list.map(it => `"${it}"`).join(",\n    ")
}

function getScript(article) {
    const attr = article.attributes;

    return `
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
${article.body}
"""

Article(
  title = "${attr.title}",
  url = "${attr.url}",
  categories = listOf(
    ${getCategories(attr.categories)}
  ),
  type = ${attr.type || "article"},
  lang = EN,
  author = "${attr.author}",
  date = LocalDate.of(${parseDate(attr.date).format("YYYY, M, D")}),
  body = body
)
`;
}
