import * as React from 'react';
import {List} from '../list/list';

const styles = require('./category.less');

export function Category({number, category}) {
  return (
    <section className={`${styles.category} ${styles[`category_block${number}`]}`}>
      <div className={styles.category_wrapper}>
        <h2 className={styles.category_title}>{category.name}</h2>
        <div className={styles.category_wrapper_lists}>
          {category.subcategories.map((subcategory, i) => {
            return <List links={subcategory} key={i}/>;
          })}
        </div>
      </div>
    </section>
  );
}
