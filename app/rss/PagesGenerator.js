const file = require('../File');
const articles = require('./articles');
const moment = require('moment');
const _ = require('lodash');

const formatDate = date => moment(date, 'MMM DD, YYYY hh:mm').format('MMM DD, YYYY');

const getFileName = name => {
    const escaped = name
        .replace(new RegExp('[/ :,\'"`+#)(-]', 'g'), '_')
        .replace(new RegExp('\\.', 'g'), '');

    return `${escaped}.html`
};

const groups = _.groupBy(articles, it => formatDate(it.date));

const getGroupLinks = group => groups[group]
    .map(article => `<li><a href="./${getFileName(article.title)}">${article.title}</a></li>`)
    .join('\n');


const links = Object
    .keys(groups)
    .map(group => `<li class="pure-menu-list-date">${group}</li>${getGroupLinks(group)}`)
    .join('\n');

const getHtml = (article) => `<!doctype html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>${article.title}</title>
  <meta content="width=device-width, initial-scale=1" name="viewport">
  <link rel="stylesheet" href="./styles.css">
</head>
<body>
<div id="layout" class="layout">
  <header class="article-nav">
    <button class="menu-link">
      <!-- Hamburger icon -->
      <span></span>
    </button>
    <div class="article-nav-links">
      <a href="${article.prev}" class="article-nav-link article-nav-link--prev">previous</a>
      <a href="${article.next}" class="article-nav-link article-nav-link--next">next</a>
    </div>
  </header>

  <aside id="menu" class="menu">
    <div class="pure-menu">
      <ul class="pure-menu-list">
        ${links}
      </ul>
    </div>
  </aside>

  <section id="main" class="main">
    <div class="main-content">
      <h1>
        <a href="${article.url}">${article.title}</a> - ${formatDate(article.date)}
      </h1>
      <h2>by ${article.author}</h2>
      ${article.description}
    </div>
  </section>

  <script>
    var menuLink = document.querySelector('.menu-link');

    var listener = function() {
      this.class = 'active';
      this.layout = document.getElementById('layout');
      this.handleEvent = function() {
        this.layout.classList.toggle(this.class);
      };
      this.handleEvent();
    };

    menuLink.addEventListener("click", function(){listener();}, false);
  </script>

  <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.3.0/styles/github.min.css">
  <script src="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.3.0/highlight.min.js"></script>
</body>
</html>
`;

articles
    .map((article, idx, array) => {
        if (idx === array.length - 1) {
            article.next = getFileName(array[0].title);
            article.prev = getFileName(array[idx - 1].title);
        } else if (idx === 0) {
            article.next = getFileName(array[idx + 1].title);
            article.prev = getFileName(array[array.length - 1].title);
        } else {
            article.next = getFileName(array[idx + 1].title);
            article.prev = getFileName(array[idx - 1].title);
        }
        return article;
    })
    .map(article => file.write(`./dist/${getFileName(article.title)}`, getHtml(article)));

file.write('./dist/styles.css', `html{font-size:18px;line-height:1.3}body{margin:0;padding:0;font-family:Arial, sans-serif}body button,body a,body *{-webkit-appearance:none}.layout,.menu,.menu-link{-webkit-transition:all 0.2s ease-out;-moz-transition:all 0.2s ease-out;-ms-transition:all 0.2s ease-out;-o-transition:all 0.2s ease-out;transition:all 0.2s ease-out}.layout{position:relative;padding-left:0}.layout.active .menu{left:20rem}.layout.active .menu-link{left:20rem;background:#F1F1F1}.layout.active .menu-link span{background-color:#888}.layout.active .menu-link span:before,.layout.active .menu-link span:after{background-color:#888}.article-nav{display:flex;flex:1;flex-wrap:wrap;flex-direction:row;position:fixed;top:0;z-index:200;width:100%;background:#49525E}.article-nav-links{display:flex;flex:1;flex-direction:row;justify-content:space-between}.article-nav-link{display:flex;position:relative;padding:0 1.5rem;line-height:3rem;color:#d1d1d1;text-decoration:none;font-weight:bold;cursor:pointer}.article-nav-link--prev{margin-left:1rem}.article-nav-link--prev:before{content:"";width:0;height:0;position:absolute;top:50%;left:0;border-style:solid;border-width:6px 8px 6px 0;border-color:transparent #d1d1d1 transparent transparent;margin-top:-6px}.article-nav-link--next{margin-right:1rem}.article-nav-link--next:after{content:"";width:0;height:0;position:absolute;top:50%;right:0;border-style:solid;border-width:6px 0 6px 8px;border-color:transparent transparent transparent #d1d1d1;margin-top:-6px}.article-nav-link:hover{color:#7d838b}.article-nav-link:hover:before{border-color:transparent #7d838b transparent transparent}.article-nav-link:hover:after{border-color:transparent transparent transparent #7d838b}.menu{margin-left:-20rem;width:20rem;position:fixed;top:0;left:0;bottom:0;padding-top:3rem;z-index:100;background:#F1F1F1}.menu-link{font-size:10px;z-index:10;width:3rem;height:3rem;padding:0 0.5rem;border:none;background:transparent;outline-color:transparent;cursor:pointer}.menu-link:hover,.menu-link:focus{outline-color:transparent}.menu-link:hover span,.menu-link:focus span{opacity:.6}.menu-link span{position:relative;display:block;background-color:white;width:100%;height:0.2rem}.menu-link span:before,.menu-link span:after{position:absolute;display:block;background-color:white;width:100%;height:0.2rem;content:" "}.menu-link span:after{top:0.5rem}.menu-link span:before{bottom:0.5rem}.pure-menu{height:100%;overflow-y:auto;-webkit-overflow-scrolling:touch}.pure-menu ul{margin:0;padding:1rem 0;list-style:none;border:none;background:transparent}.pure-menu li{padding:.5rem 1rem}.pure-menu li.pure-menu-list-date{background-color:#E4E4E4;border-bottom:1px solid #f1f1f1;border-bottom:1px solid #d7d7d7;padding-top:1rem;padding-bottom:1rem;font-weight:bold;margin:.5rem 0}.pure-menu a{color:black;text-decoration:none}.pure-menu a:hover{color:#888}.main{padding-top:3rem;margin-bottom:10rem}.main-content{margin:0 auto;padding:0 2rem;max-width:45rem}@media screen and (max-width: 480px){.main-content{padding:0 1rem}}.main li>p{margin:0}.main .hljs{width:100%;overflow:auto;display:block;margin:0 auto}.main img{max-width:100%;display:block;margin:0 auto}.main code{max-width:100%;vertical-align:bottom;overflow:auto;display:inline-block;background-color:#f8f8f8}@media screen and (max-width: 480px){.main code{margin:0.5rem -1rem;padding:0.5rem 1rem}}.main h1{font-size:1.6rem;margin:1em 0}.main h2{font-size:1.5rem;margin:1em 0}.main h3{font-size:1.4rem;margin:1em 0}.main h4{font-size:1.3rem;margin:1em 0}.main h5{font-size:1.2rem;margin:1em 0}.main h6{font-size:1.1rem;margin:1em 0}`);
