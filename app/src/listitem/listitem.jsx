import React from 'react';
import styles from './listitem.less';

export default class Listitem extends React.Component {
    getStars() {
        if (this.props.link.star) {
            return (
                <span className={styles.listitem_star}>
                    <span className={styles.listitem_star_count}>{this.props.link.star}</span>
                    <img src={require("./star.svg")}
                         alt="Star"
                         className={styles.listitem_star_icon}/>
                </span>
            )
        }
    }

    render() {
        return (
            <li className={styles.listitem}>
              {this.getStars()}
                <a href={this.props.link.href}
                   target="_blank"
                   rel="nofollow noopener"
                   className={styles.listitem_link}>
                    {this.props.link.name}
                </a>
                <p className={styles.listitem_description}>{this.props.link.desc}</p>
            </li>
        );
    }
}
