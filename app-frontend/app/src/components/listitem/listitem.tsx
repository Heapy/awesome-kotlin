import * as React from "react";
import {Link, LinkState} from "../../model";
import {classes} from "../../styles";
import "./listitem.less";

function split(str: string): string {
  return str.replace("/", "/​"); // replace "/" with "/ and https://en.wikipedia.org/wiki/Zero-width_space"
}

export function Listitem({link}: ListitemProps) {
  return (
    <li className={classes({
      ["listitem"]: true,
      ["listitem_archived"]: link.state === LinkState.ARCHIVED,
      ["listitem_unsupported"]: link.state === LinkState.UNSUPPORTED
    })}>

      {getStars(link)}

      <a href={link.href}
         target="_blank"
         title={getTitle(link)}
         rel="nofollow noopener"
         className="listitem_link">
        {split(link.name)}
      </a>

      {getLastUpdated(link)}
      <span className="listitem_description"
            dangerouslySetInnerHTML={{__html: link.desc}}/>

    </li>
  );
}

interface ListitemProps {
  readonly link: Link;
}

function getTitle(link: Link): string {
  if (link.state === LinkState.AWESOME) {
    return `[awesome] ${link.name}`;
  } else if (link.state === LinkState.UNSUPPORTED) {
    return `[unsupported] ${link.name}`;
  } else if (link.state === LinkState.ARCHIVED) {
    return `[archived] ${link.name}`;
  } else {
    return link.name;
  }
}

function getStars(link) {
  if (link.star) {
    return (
      <span className="listitem_star">
        <span className="listitem_star_count">{link.star}</span>
        <img src={require("./star.svg")}
             alt="Star"
             className="listitem_star_icon"/>
      </span>
    );
  }
}

function getLastUpdated(link) {
  if (link.update) {
    return (
      <p className="listitem_description">{`Last update: ${link.update}`}</p>
    );
  }
}
