import React from 'react';
import styles from './listitem.less';

export default class Listitem extends React.Component {
    getStars() {
        if (this.props.link.star) {
            return (
                <span className={styles.listitem_star}>
                    <img src={require("./star.svg")}
                         alt="Star"
                         className={styles.listitem_star_icon}/>
                    <span>{this.props.link.star}</span>
                </span>
            )
        }
    }

    render() {
        return (
            <li className={styles.listitem}>
                <a href={this.props.link.href} className={styles.listitem_link}>{this.props.link.name}</a>
                {this.getStars()}
                <p className={styles.listitem_description}>{this.props.link.desc}</p>
            </li>
        );
    }
}
