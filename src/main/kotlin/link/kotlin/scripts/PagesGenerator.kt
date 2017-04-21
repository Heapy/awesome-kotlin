package link.kotlin.scripts

import link.kotlin.scripts.ArticleFeature.highlightjs
import link.kotlin.scripts.ArticleFeature.mathjax
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

interface PagesGenerator {
    fun generate(articles: List<Article>)
}

private fun getGroupLinks(group: List<Article>): String {
    return group
        .map { """<li><a href="./${it.filename}">${it.title}</a></li>""" }
        .joinToString(separator = "\n")
}
class DefaultPageGenerator : PagesGenerator {
    override fun generate(articles: List<Article>) {
        val links = articles
            .groupBy { formatDate(it.date) }
            .map { """<li class="pure-menu-list-date">${it.key}</li>${getGroupLinks(it.value)}""" }
            .joinToString(separator = "\n")

        articles
            .mapIndexed { idx, article ->
                if (idx == articles.size - 1) {
                    article.copy(
                        next = articles[0].filename,
                        prev = articles[idx - 1].filename
                    )
                } else if (idx == 0) {
                    article.copy(
                        next = articles[idx + 1].filename,
                        prev = articles[articles.size - 1].filename
                    )
                } else {
                    article.copy(
                        next = articles[idx + 1].filename,
                        prev = articles[idx - 1].filename
                    )
                }
            }
            .forEach {
                Files.write(
                    Paths.get("./dist/articles/${it.filename}"),
                    getHtml(it, links).toByteArray(),
                    StandardOpenOption.CREATE
                )
            }
    }
}

internal val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
private fun formatDate(date: LocalDate): String {
    return date.format(formatter)
}

private fun getHtml(article: Article, links: String): String {
    return """<!doctype html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>${article.title}</title>
  <meta content="width=device-width, initial-scale=1" name="viewport">
  <link rel="stylesheet" href="/styles.css">
  <link href="https://fonts.googleapis.com/css?family=Anonymous+Pro:400,700" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Open+Sans:400,300,600" rel="stylesheet" type="text/css">
  <link rel="alternate" type="application/rss+xml" title="Kotlin.Link - 20 latest" href="/rss.xml" />
  <link rel="alternate" type="application/rss+xml" title="Kotlin.Link - full archive" href="/rss-full.xml" />
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
        $links
      </ul>
    </div>
  </aside>

  <section id="main" class="main">
    <div class="main-content">
      <h1>
        <a href="${article.url}">${article.title}</a> - <span class="main-title-date">${formatDate(article.date)}</span>
      </h1>
      <h2>by ${article.author}</h2>
      ${article.body}
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

 <!-- Yandex.Metrika counter -->
  <script type="text/javascript">
    (function (d, w, c) {
      (w[c] = w[c] || []).push(function() {
        try {
          w.yaCounter35722810 = new Ya.Metrika({
            id:35722810,
            webvisor:true,
            clickmap:true,
            trackLinks:true,
            accurateTrackBounce:true
          });
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
  <noscript>
    <div><img src="//mc.yandex.ru/watch/35722810" style="position:absolute; left:-9999px;" alt="" /></div>
  </noscript>
  <!-- /Yandex.Metrika counter -->
</body>
</html>
"""
}

fun getFeatures(features: List<ArticleFeature>): String {
    return features.map {
        when (it) {
            mathjax -> """<script src="https://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML"></script>"""
            highlightjs -> """
                <link rel="stylesheet" href="/github.css">
                <script src="/highlight.pack.js"></script>
                <script>hljs.initHighlightingOnLoad();</script>
                """
            else -> """<script type="application/javascript">console.error("Unknown feature: $it")</script>"""
        }
    }.joinToString(separator = "\n")
}
