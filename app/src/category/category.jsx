import React from 'react';
import styles from './category.less';
import Warp from '../warp/warp.jsx';

export default class Category extends React.Component {
    render() {
        return  (
          <div>
          <section className={`${styles.category} ${styles.category_block1}`}>
                  <div className={styles.category_wrapper}>
                    <h2>Category</h2>
                    <Warp />
                    <Warp />
                    <Warp />
                    <Warp />
                    <Warp />
                  </div>
                </section>

            <section className={`${styles.category} ${styles.category_block2}`}>
              <div className={styles.category_wrapper}>
                <h2>Category</h2>
                <Warp />
                <Warp />

              </div>
            </section>

            <section className={`${styles.category} ${styles.category_block3}`}>
              <div className={styles.category_wrapper}>
                <h2>Category</h2>
                <Warp />
                <Warp />
                <Warp />

              </div>
            </section>

            <section className={`${styles.category} ${styles.category_block4}`}>
              <div className={styles.category_wrapper}>
                <h2>Category</h2>
                <Warp />

              </div>
            </section>

            <section className={`${styles.category} ${styles.category_block5}`}>
              <div className={styles.category_wrapper}>
                <h2>Category</h2>
                <Warp />
                <Warp />
                <Warp />
                <Warp />
                <Warp />
                <Warp />
                <Warp />
                <Warp />

              </div>
            </section>



            <section className={`${styles.category} ${styles.category_block6}`}>
              <div className={styles.category_wrapper}>
                <h2>Category</h2>
                <Warp />

              </div>
            </section>          </div>
        );
    }
}
