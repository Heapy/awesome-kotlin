const file = require('../File');
const articles = require('./articles');
const moment = require('moment');

const getFileName = name => {
    const escaped = name
        .replace(new RegExp('[/ :,\'"`+#)(-]', 'g'), '_')
        .replace(new RegExp('\\.', 'g'), '');

    return `${escaped}.html`
};

const links = articles
    .map(article => `<li><a href="./${getFileName(article.title)}">${article.title}</a></li>`)
    .join('\n');

const getHtml = (article) => `
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>${article.title}</title>
<style>
  img {
    max-width: 900px;
    display: block;
    margin: 0 auto; 
  }
  
  body {
    display:flex;
    flex-direction: row;
    flex-wrap: wrap;
    justify-content: center;
    align-content: flex-start;
    align-items: flex-start;
  }
  .content {
    font-size: 1.5em;
    max-width: 1000px;
    padding-top: 30px;
    margin: 0 auto;
  }
  .nav {
    float: left;
  }
</style>
</head>
<body>
  <aside class="nav">
    <ol>
      ${links}
    </ol>
  </aside>
  <section class="content">
      <h1><a href="${article.url}">${article.title}</a> - ${moment(article.date, 'MMM DD, YYYY hh:mm').format('MMM DD, YYYY')}</h1>
      <h2>by ${article.author}</h2>
    ${article.description}
  </section>
  <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.3.0/styles/github.min.css">
  <script src="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.3.0/highlight.min.js"></script>
</body>
</html>
`;

articles
    .map(article => file.write(`./dist/${getFileName(article.title)}`, getHtml(article)));
