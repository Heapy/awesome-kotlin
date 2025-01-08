import * as React from "react";
import "./navigation.scss"
import NavLink from "../NavLink";

export function Navigation({links}: Props) {
  return (
    <section className="navigation_bar">
      <div className="navigation_bar_wrapper">
        {links.map((link, idx, array) => (
          <React.Fragment key={idx}>
            <AnyLink link={link}/>
            {idx !== array.length - 1 && <span className="separator">|</span>}
          </React.Fragment>
        ))}
      </div>
    </section>
  );
}

function AnyLink({link}: { link: NavigationLink }) {
  if (link.href.startsWith("https://")) {
    return (
      <a
        href={link.href}
        className="link"
        target="_blank"
        title={link.title}
        rel="noopener noreferrer">
        {link.name}
      </a>
    );
  } else {
    return (
      <NavLink
        className="link"
        to={link.href}>
        {link.name}
      </NavLink>
    );
  }
}

export interface NavigationLink {
  readonly href: string;
  readonly name: string;
  readonly title?: string;
}

export interface Props {
  readonly links: NavigationLink[]
}
