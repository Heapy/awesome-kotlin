const moment = require('moment');
const _ = require('lodash');
/**
 * type [{
 *  author:string,
 *  categories:[],
 *  date:string,
 *  type: [],
 *  description:string,
 *  file:string, // input filename
 *  title:string,
 *  filename:string. // output filename
 *  url:strign
 * }]
 */
const articles = require('./articles');

const formatDate = date => moment(date, 'MMM DD, YYYY hh:mm').format('MMMM YYYY');

const getCategoryName = name => {
    switch (name) {
        case 'webinar':
            return 'Webinars';
        case 'video':
            return 'Videos';
        default:
            return 'Articles, Blog Posts';
    }
};

const getCategory = entities => {
    const groups = _.groupBy(entities, it => formatDate(it.date));

    const subcategories = Object
        .keys(groups)
        .map(group => {
            return {
                name: group,
                links: groups[group].map(it => {
                    return {
                        name: it.title,
                        desc: formatDate(it.date),
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
};

const getCategories = categories => categories.map(category => getCategory(category));

const posts = articles.filter(it => !it.type);
const webinars = articles.filter(it => it.type === 'webinar');
const videos = articles.filter(it => it.type === 'video');
const slides = articles.filter(it => it.type === 'slides');


module.exports = getCategories([posts, webinars]);
