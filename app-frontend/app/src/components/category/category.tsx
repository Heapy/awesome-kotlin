import * as React from "react";
import Subcategories from "../list/list";
import "./category.scss";
import {memo} from "react";

function Category({category}) {
  return (
    <section className="category">
      <div className="category_wrapper">
        <h2 className="category_title">{category.name}</h2>
        <div className="category_wrapper_lists">
          {category.subcategories.map((subcategory, i) => {
            return (
              <Subcategories
                key={category.name}
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

export default memo(Category)
