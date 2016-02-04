import React from 'react';
import styles from './list.less';
import Listitem from '../listitem/listitem.jsx';

export default class List extends React.Component {
    render() {
        return (
            <section className={styles.list}>
                <h3 className={styles.list_title}>{this.props.links.name}</h3>
                <ul className={styles.list_list}>
                    {this.props.links.links.map((link, i) => {
                        return <Listitem link={link} key={i}/>;
                    })}
                </ul>
            </section>
        );
    }
}
