import * as React from "react";
import {Subcategories} from "../list/list";
import "./category.less";

export function Category({category}) {
  return (
    <section className="category">
      <div className="category_wrapper">
        <h2 className="category_title">{category.name}</h2>
        <div className="category_wrapper_lists">
          {category.subcategories.map((subcategory, i) => {
            return (
              <Subcategories
                key={i}
                prefix={category.name}
                subcategory={subcategory}
              />
            );
          })}
        </div>
      </div>
    </section>
  );
}
