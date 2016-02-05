import React from 'react';
import styles from './page.less';
import Head from '../head/head.jsx';
import Search from '../search/search.jsx';
import Category from '../category/category.jsx';

export default class Page extends React.Component {
    constructor(props) {
        super(props);
        this.state = {data: this.props.categories};
    }

    filterData(data, value) {
        return data.filter(it => it.name === value);
    }

    onSearchValueChanged(value) {
        if (value) {
            this.setState({data: this.filterData(this.props.categories, value)});
        } else {
            this.setState({data: this.props.categories});
        }
    }

    render() {
        return (
            <div>
                <a href="https://github.com/JavaBy/awesome-kotlin">
                    <img className={styles.page_github_link}
                         src={require('./forkme_right_white_ffffff.png')}
                         alt="Fork me on GitHub"/></a>
                <Head />
                <Search onChange={this.onSearchValueChanged.bind(this)}  />
                {this.state.data.map((category, i) => {
                    return <Category category={category} key={i}/>;
                })}
            </div>
        );
    }
}
