import * as React from "react";
import {useState} from "react";
import {Header} from "../head/head";
import {Search} from "../search/search";
import {Category} from "../category/category";
import {Bar} from "../bar/bar";
import {Links} from "../../model";
import {Navigation} from "../navigation/Navigation";
import "./page_wrapper.less";

const versions = require("../../../versions.json");

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

function filterData(categories: Links, value) {
  const searchTerm = toLower(value);

  return categories.reduce(function (acc: Links, category) {
    const categoryFiltered = reduceCategory(category, searchTerm);

    if (categoryFiltered.subcategories.length) {
      acc.push(categoryFiltered);
    }

    return acc;
  }, []);
}

export function LinksPageComponent(props: PageProps) {
  const [data, setData] = useState(props.displayLinks);

  function onSearchValueChanged(value: string) {
    if (value) {
      setData(filterData(props.searchLinks, value))
    } else {
      setData(props.displayLinks)
    }
  }

  return (
    <div>
      <Navigation links={[
        {
          name: "Essential",
          href: "/",
        },
        {
          name: "Everything",
          href: "/resources",
        },
        {
          name: "Kotlin User Groups",
          href: "/kugs",
        },
        {
          name: "Github",
          title: "Fork me!",
          href: "https://github.com/KotlinBy/awesome-kotlin",
        },
      ]}/>

      <Header/>

      <Search onChange={onSearchValueChanged}/>

      <Bar versions={versions}/>

      {data.map((category, i) => {
        return <Category category={category} key={i}/>;
      })}
    </div>
  );
}


interface PageProps {
  readonly displayLinks: Links;
  readonly searchLinks: Links;
}

function toLower(string) {
  return string.toLowerCase();
}

function matches(search, text) {
  return text && toLower(text).includes(toLower(search));
}
