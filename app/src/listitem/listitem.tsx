import * as React from 'react';

const styles = require('./listitem.less');

function split(str: string): string {
  return str.replace("/", "/​"); // replace "/" with "/ and https://en.wikipedia.org/wiki/Zero-width_space"
}

export function Listitem({link}) {
  return (
    <li className={styles.listitem}>

      {getStars(link)}

      <a href={link.href}
         target="_blank"
         title={link.name}
         rel="nofollow noopener"
         className={styles.listitem_link}>
        {split(link.name)}
      </a>

      {getLastUpdated(link)}
      <p className={styles.listitem_description}>{link.desc}</p>

    </li>
  )
}

function getStars(link) {
  if (link.star) {
    return (
      <span className={styles.listitem_star}>
        <span className={styles.listitem_star_count}>{link.star}</span>
        <img src={require("./star.svg")}
             alt="Star"
             className={styles.listitem_star_icon}/>
      </span>
    )
  }
}

function getLastUpdated(link) {
  if (link.update) {
    return (
      <p className={styles.listitem_description}>{`Last update: ${link.update}`}</p>
    )
  }
}
