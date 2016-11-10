import * as React from 'react';
import {Head} from '../head/head';
import {Search} from '../search/search';
import {Category} from '../category/category';

const styles = require('./page.less');

const toLower = string => string.toLowerCase();
const matches = (search, text) => {
  return text && toLower(text).includes(toLower(search));
};

export class Page extends React.Component<PageProps, PageState> {
  constructor(props) {
    super(props);
    this.state = {data: this.props.categories};
  }

  filterData(categories, value) {
    const clonedCategories = JSON.parse(JSON.stringify(categories));
    const searchTerm = toLower(value);

    return clonedCategories.filter(category => {
      category.subcategories = category.subcategories.filter(subcategory => {
        subcategory.links = subcategory.links.filter(link => {
          if (matches(searchTerm, link.name)) {
            return true;
          } else if (matches(searchTerm, link.desc)) {
            return true
          } else if (link.tags && link.tags.filter(tag => matches(searchTerm, tag)).length) {
            return true;
          } else {
            return false;
          }
        });

        return subcategory.links.length;
      });

      return category.subcategories.length;
    });
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
        <a href="https://github.com/KotlinBy/awesome-kotlin">
          <img className={styles.page_github_link}
               src={require('./forkme_right_white_ffffff.png')}
               alt="Fork me on GitHub"/></a>
        <Head />
        <Search onChange={this.onSearchValueChanged.bind(this)}/>
        {this.state.data.map((category, i) => {
          return <Category category={category} number={i} key={i}/>;
        })}
      </div>
    );
  }
}

interface PageProps {
  readonly categories: any;
}

interface PageState {
  readonly data: any;
}
