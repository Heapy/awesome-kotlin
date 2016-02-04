import React from 'react';
import styles from './listitem.less';

export default class Listitem extends React.Component {
    render() {
        return  (
          <li className={styles.listitem}>
            <a className={styles.listitem_link}>name/rep</a>
            <span className={styles.listitem_star}>
              <img src={require("./star.svg")}
                   alt="Star"
                   className={styles.listitem_star_icon}/>
            </span>
            <p className={styles.listitem_description}>Nanananann Batman</p>
          </li>
        );
    }
}
