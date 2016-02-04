import React from 'react';
import styles from './warp.less';
import Listitem from '../listitem/listitem.jsx';

export default class Warp extends React.Component {
    render() {
        return  (

          <section className={styles.warp}>
                  <h3 className={styles.warp_title}>Warp</h3>
                  <ul className={styles.warp_list}>
                    <Listitem />
                    <Listitem />
                    <Listitem />
                    <Listitem />
                    <Listitem />
                    //todo
                  </ul>
          </section>
        );
    }
}
