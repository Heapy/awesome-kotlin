import React from 'react';
import styles from './head.less';

export default class Head extends React.Component {
    render() {
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
}
