import * as React from "react";
import {Listitem} from "../listitem/listitem";
import {withRouter} from "react-router";

const styles = require("./list.less");

class SubcategoriesComponent extends React.Component<SubcategoriesProps, SubcategoriesState> {
  constructor() {
    super();
  }

  render() {
    const {subcategory} = this.props;

    return (
      <section ref="subcategory" className={styles.list}>
        <h3 className={styles.list_title}>{subcategory.name}</h3>
        <ul className={styles.list_list}>
          {
            subcategory.links
              .sort((a, b) => b.star - a.star)
              .map((link, i) => <Listitem link={link} key={i}/>)
          }
        </ul>
      </section>
    );
  }
}

export const Subcategories = withRouter(SubcategoriesComponent);

interface SubcategoriesProps {
  location?: any;
  subcategory: any;
}

interface SubcategoriesState {
}
