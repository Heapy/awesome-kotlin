import React from 'react';
import styles from './search.less';

export default class Search extends React.Component {
    render() {
        return  <section className={styles.search}>
                    <form className={styles.search_wrapper}>
                        <input className={styles.search_field}/>
                    </form>
                </section>;
    }
}
