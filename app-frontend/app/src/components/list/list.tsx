import * as React from "react";
import {Listitem} from "../listitem/listitem";
import {Link, LinkState, Subcategory} from "../../model";
import "./list.less";

export class Subcategories extends React.Component<SubcategoriesProps, SubcategoriesState> {
  render() {
    const {subcategory} = this.props;

    return (
      <section ref="subcategory" className="list">
        <h3 id={getAnchor(this.props.prefix, subcategory.name)} className="list_title">
          <a href={`#${getAnchor(this.props.prefix, subcategory.name)}`}>{subcategory.name}</a>
        </h3>
        <ul className="list_list">
          {renderLinks(subcategory.links.filter(link => link.state === LinkState.AWESOME))}
          {renderLinks(subcategory.links.filter(link => link.state === LinkState.DEFAULT))}
          {renderLinks(subcategory.links.filter(link => link.state === LinkState.ARCHIVED))}
          {renderLinks(subcategory.links.filter(link => link.state === LinkState.UNSUPPORTED))}
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
