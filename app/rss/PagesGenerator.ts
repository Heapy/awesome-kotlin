import {write} from '../File';
import {articles} from './articles';
import * as _ from 'lodash';
import * as moment from 'moment';
import * as sass from 'node-sass';

const groups = _.groupBy(articles, it => formatDate(it.date));

const getGroupLinks = group => groups[group]
  .map(article => `<li><a href="./${article.filename}">${article.title}</a></li>`)
  .join('\n');

const links = Object
  .keys(groups)
  .map(group => `<li class="pure-menu-list-date">${group}</li>${getGroupLinks(group)}`)
  .join('\n');

articles
  .map((article, idx, array) => {
    if (idx === array.length - 1) {
      article.next = array[0].filename;
      article.prev = array[idx - 1].filename;
    } else if (idx === 0) {
      article.next = array[idx + 1].filename;
      article.prev = array[array.length - 1].filename;
    } else {
      article.next = array[idx + 1].filename;
      article.prev = array[idx - 1].filename;
    }
    return article;
  })
  .forEach(article => write(`./dist/articles/${article.filename}`, getHtml(article)));

const style = sass.renderSync({
  file: './app/rss/styles.scss',
  outputStyle: 'compressed'
});

write('./dist/articles/styles.css', style.css);

function getHtml(article: Article): string {
  return `<!doctype html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>${article.title}</title>
  <meta content="width=device-width, initial-scale=1" name="viewport">
  <link rel='stylesheet' href='./styles.css'>
  <link href='https://fonts.googleapis.com/css?family=Anonymous+Pro:400,700' rel='stylesheet' type='text/css'>
  <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,300,600' rel='stylesheet' type='text/css'>
  <link rel="alternate" type="application/rss+xml" title="Kotlin.Link - 20 latest" href="/rss.xml" />
  <link rel="alternate" type="application/rss+xml" title="Kotlin.Link - full archive"href="/rss-full.xml" />
  ${getFeatures(article.features)}
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
        <a href="${article.url}">${article.title}</a> - <span class="main-title-date">${formatDate(article.date)}</span>
      </h1>
      <h2>by ${article.author}</h2>
      ${article.description}
      
      <div class="main-content-backlink">
        <hr/>
        <a href='http://kotlin.link/'>&larr; Back to kotlin.link</a>
      </div>
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
  <!-- Yandex.Metrika counter -->
  <script type="text/javascript">
    (function (d, w, c) {
      (w[c] = w[c] || []).push(function() {
        try {
          w.yaCounter35722810 = new Ya.Metrika({id:35722810,
            webvisor:true,
            clickmap:true,
            trackLinks:true,
            accurateTrackBounce:true});
        } catch(e) { }
      });

      var n = d.getElementsByTagName("script")[0],
          s = d.createElement("script"),
          f = function () { n.parentNode.insertBefore(s, n); };
      s.type = "text/javascript";
      s.async = true;
      s.src = (d.location.protocol == "https:" ? "https:" : "http:") + "//mc.yandex.ru/metrika/watch.js";

      if (w.opera == "[object Opera]") {
        d.addEventListener("DOMContentLoaded", f, false);
      } else { f(); }
    })(document, window, "yandex_metrika_callbacks");
  </script>
  <noscript><div><img src="//mc.yandex.ru/watch/35722810" style="position:absolute; left:-9999px;" alt="" /></div></noscript>
  <!-- /Yandex.Metrika counter -->
</body>
</html>
`;
}

function formatDate(date: string): string {
  return moment(date, 'MMM DD, YYYY hh:mm').format('MMM DD, YYYY');
}

function getFeatures(features: ArticleFeature[] = []): string {
  return features
    .map(feature => {
      switch (feature) {
        case 'mathjax':
          return '<script src="https://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML"></script>';
        default:
          return `<script type="application/javascript">console.error("Unknown feature: ${feature}")</script>`;
      }
    })
    .join('\n');
}
