import * as React from 'react';
import {Listitem} from '../listitem/listitem';

const styles = require('./list.less');

export function List({links}) {
  return (
    <section className={styles.list}>
      <h3 className={styles.list_title}>{links.name}</h3>
      <ul className={styles.list_list}>
        {
          links.links
            .sort((a, b) => b.star - a.star)
            .map((link, i) => <Listitem link={link} key={i}/>)
        }
      </ul>
    </section>
  );
}
