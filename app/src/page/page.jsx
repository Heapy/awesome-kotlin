import React from 'react';
import styles from './page.less';
import Head from '../head/head.jsx';
import Search from '../search/search.jsx';
import Category from '../category/category.jsx';

export default class Page extends React.Component {
    render() {
        return (
            <div>
                <a href="https://github.com/JavaBy/awesome-kotlin">
                    <img className={styles.page_github_link}
                         src={require('./forkme_right_white_ffffff.png')}
                         alt="Fork me on GitHub"/></a>
                <Head />
                <Search />
                {this.props.categories.map((category, i) => {
                    return <Category category={category} key={i}/>;
                })}
            </div>
        );
    }
}
