import * as _ from 'lodash';
import {formatDate} from '../Utils';
import {articles} from './articles';

function getCategoryName(name: ArticleType) {
  switch (name) {
    case 'webinar':
      return 'Webinars';
    case 'slides':
      return 'Slides';
    case 'video':
      return 'Videos';
    default:
      return 'Articles, Blog Posts';
  }
}

function getCategory(entities:Article[]) {
  const groups = _.groupBy(entities, it => formatDate(it.date));

  const subcategories = Object
    .keys(groups)
    .map(group => {
      return {
        name: group,
        links: groups[group].map(it => {
          return {
            name: it.title,
            desc: it.author,
            href: `http://kotlin.link/articles/${it.filename}`,
            type: it.type || 'article',
            tags: it.categories
          }
        })
      }
    });

  return {
    name: getCategoryName(entities[0].type),
    subcategories: subcategories
  }
}

const getCategories = categories => categories.map(getCategory);

const posts = articles.filter(it => !it.type);
const webinars = articles.filter(it => it.type === 'webinar');
const videos = articles.filter(it => it.type === 'video');
const slides = articles.filter(it => it.type === 'slides');

export const articlesLinks = getCategories([posts, webinars, videos, slides]);
