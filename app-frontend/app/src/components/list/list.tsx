import * as React from "react";
import {Listitem} from "../listitem/listitem";
import {Link, Subcategory} from "../../model";

const styles = require("./list.less");

export class Subcategories extends React.Component<SubcategoriesProps, SubcategoriesState> {
  render() {
    const {subcategory} = this.props;

    return (
      <section ref="subcategory" className={styles.list}>
        <h3 id={getAnchor(this.props.prefix, subcategory.name)} className={styles.list_title}>
          <a href={`#${getAnchor(this.props.prefix, subcategory.name)}`}>{subcategory.name}</a>
        </h3>
        <ul className={styles.list_list}>
          {renderLinks(subcategory.links.filter(link => !link.archived && !link.unsupported))}
          {renderLinks(subcategory.links.filter(link => link.unsupported))}
          {renderLinks(subcategory.links.filter(link => link.archived && !link.unsupported))}
        </ul>
      </section>
    );
  }
}

function getAnchor(prefix: string, suffix: string): string {
  const normalizedPrefix = prefix.replace(" ", "_").toLowerCase()
  const normalizedSuffix = suffix.replace(" ", "_").toLowerCase()

  return `${normalizedPrefix}-${normalizedSuffix}`
}

function renderLinks(links: Link[]) {
  return links.sort((a, b) => b.star - a.star)
    .map((link, i) => <Listitem link={link} key={i}/>)
}

interface SubcategoriesProps {
  readonly prefix: string;
  readonly subcategory: Subcategory;
}

interface SubcategoriesState {
}
