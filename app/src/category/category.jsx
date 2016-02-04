import React from 'react';
import styles from './category.less';
import List from '../list/list.jsx';

export default class Category extends React.Component {
    render() {
        return (
            <section className={`${styles.category} ${styles.category_block1}`}>
                <div className={styles.category_wrapper}>
                    <h2>{this.props.category.name}</h2>
                    <hr/>
                    <div className={styles.category_wrapper_lists}>
                        {this.props.category.subcategories.map((subcategory, i) => {
                            return <List links={subcategory} key={i}/>;
                        })}
                    </div>
                </div>
            </section>
        );
    }
}
