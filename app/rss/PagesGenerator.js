const file = require('../File');
const articles = require('./articles');
const moment = require('moment');

const getHtml = (article) => `
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>${article.title}</title>
<style>
  .content {
    font-size: 1.5em;
    max-width: 1000px;
    padding-top: 30px;
    margin: 0 auto;
  }
</style>
</head>
<body>
  <section class="content">
      <h1><a href="${article.url}">${article.title}</a> - ${moment(article.date, 'MMM DD, YYYY hh:mm').format('MMM DD, YYYY')}</h1>
      <h2>by ${article.author}</h2>
    ${article.description}
  </section>
  <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.3.0/styles/default.min.css">
  <script src="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.3.0/highlight.min.js"></script>
</body>
</html>
`;


const getFileName = name => {
    const escaped = name
        .replace(new RegExp('[/ :,\'"`+-]', 'g'), '_')
        .replace(new RegExp('\\.', 'g'), '');

    return `./dist/pages/${escaped}.html`
};

articles
    .forEach(article =>
        file.write(getFileName(article.title), getHtml(article)));
