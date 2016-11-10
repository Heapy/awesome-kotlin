import * as React from 'react';

const styles = require('./head.less');

export function Head() {
  return (
    <section className={styles.head}>
      <div className={styles.head_wrapper}>
        <img src={require("./kotlin.svg")}
             alt="Kotlin Language Logo"
             className={styles.head_logo}/>
      </div>
    </section>
  );
}
