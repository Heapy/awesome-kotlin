import * as React from "react";
import {Head} from "../head/head";
import {Search} from "../search/search";
import {Category} from "../category/category";
import {withRouter} from "react-router";
import {searchString} from "../locations";

const styles = require("./page.less");

const data = require("../../LinksWithStars.json");

function reduceCategory(category, searchTerm) {
  const subcategories = category.subcategories.reduce(function (acc, subcategory) {
    const subcategoryFiltered = reduceSubcategory(subcategory, searchTerm);

    if (subcategoryFiltered.links.length) {
      acc.push(subcategoryFiltered);
    }

    return acc;
  }, []);

  return {
    name: category.name,
    subcategories
  };
}

function reduceSubcategory(subcategory, searchTerm) {
  const links = subcategory.links.reduce(function (acc, link) {
    if (linkMatches(link, searchTerm)) {
      acc.push(link);
    }
    return acc;
  }, []);

  return {
    name: subcategory.name,
    links
  };
}

function linkMatches(link, searchTerm) {
  if (matches(searchTerm, link.name)) {
    return true;
  } else if (matches(searchTerm, link.desc)) {
    return true;
  } else if (link.tags && link.tags.filter(tag => matches(searchTerm, tag)).length) {
    return true;
  }

  return false;
}

function filterData(categories, value) {
  const searchTerm = toLower(value);

  return categories.reduce(function (acc, category) {
    const categoryFiltered = reduceCategory(category, searchTerm);

    if (categoryFiltered.subcategories.length) {
      acc.push(categoryFiltered);
    }

    return acc;
  }, []);
}

class PageComponent extends React.Component<PageProps, PageState> {
  constructor(props) {
    super(props);
    this.state = {data: data};
  }

  onSearchValueChanged = (value: any): void => {
    this.props.history.push({
      search: searchString({...this.props.match.params, q: value})
    });

    if (value) {
      this.setState({data: filterData(data, value)});
    } else {
      this.setState({data: data});
    }
  };

  render() {
    return (
      <div>
        <a href="https://github.com/KotlinBy/awesome-kotlin">
          <img className={styles.page_github_link}
               src={require("./forkme_right_white_ffffff.png")}
               alt="Fork me on GitHub"/></a>

        <Head/>

        <Search onChange={this.onSearchValueChanged}/>

        {this.state.data.map((category, i) => {
          return <Category category={category} key={i}/>;
        })}
      </div>
    );
  }
}

interface PageProps {
  history: any;
  match: any;
}

interface PageState {
  readonly data: any;
}

export const Page = withRouter(PageComponent);

function toLower(string) {
  return string.toLowerCase();
}

function matches(search, text) {
  return text && toLower(text).includes(toLower(search));
}
