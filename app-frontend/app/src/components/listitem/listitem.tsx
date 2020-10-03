import * as React from "react";
import {Link} from "../../model";
import {classes} from "typestyle";

const styles = require("./listitem.less");

function split(str: string): string {
  return str.replace("/", "/​"); // replace "/" with "/ and https://en.wikipedia.org/wiki/Zero-width_space"
}

export function Listitem({link}: ListitemProps) {
  return (
    <li className={classes(styles.listitem, {[styles.listitem_archived]: link.archived, [styles.listitem_unsupported]: link.unsupported})}>

      {getStars(link)}

      <a href={link.href}
         target="_blank"
         title={getTitle(link)}
         rel="nofollow noopener"
         className={styles.listitem_link}>
        {split(link.name)}
      </a>

      {getLastUpdated(link)}
      <span className={styles.listitem_description}
            dangerouslySetInnerHTML={{__html: link.desc}}/>

    </li>
  );
}

interface ListitemProps {
  readonly link: Link;
}

function getTitle(link: Link): string {
  if (link.archived) {
    return `[archived] ${link.name}`
  } else if (link.unsupported) {
    return `[unsupported] ${link.name}`
  } else {
    return link.name
  }
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
    );
  }
}

function getLastUpdated(link) {
  if (link.update) {
    return (
      <p className={styles.listitem_description}>{`Last update: ${link.update}`}</p>
    );
  }
}
