import * as React from "react";
import {Listitem} from "../listitem/listitem";
import {Link, Subcategory} from "../../model";

const styles = require("./list.less");

export class Subcategories extends React.Component<SubcategoriesProps, SubcategoriesState> {
  render() {
    const {subcategory} = this.props;

    return (
      <section ref="subcategory" className={styles.list}>
        <h3 className={styles.list_title}>{subcategory.name}</h3>
        <ul className={styles.list_list}>
          {renderLinks(subcategory.links.filter(link => !link.archived))}
          {renderLinks(subcategory.links.filter(link => link.archived))}
        </ul>
      </section>
    );
  }
}

function renderLinks(links: Link[]) {
  return links.sort((a, b) => b.star - a.star)
    .map((link, i) => <Listitem link={link} key={i}/>)
}

interface SubcategoriesProps {
  subcategory: Subcategory;
}

interface SubcategoriesState {
}
