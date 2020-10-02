import * as React from "react";
import {Subcategories} from "../list/list";

const styles = require("./category.less");

export function Category({category}) {
  return (
    <section className={styles.category}>
      <div className={styles.category_wrapper}>
        <h2 className={styles.category_title}>{category.name}</h2>
        <div className={styles.category_wrapper_lists}>
          {category.subcategories.map((subcategory, i) => {
            return (
              <Subcategories
                key={i}
                subcategory={subcategory}
              />
            );
          })}
        </div>
      </div>
    </section>
  );
}
