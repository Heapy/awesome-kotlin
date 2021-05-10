import * as React from "react";
import {NavLink} from "react-router-dom";
import "./Navigation.less"

export function Navigation(props: Props) {
  return (
    <section className="navigation_bar">
      <div className="navigation_bar_wrapper">
        <NavLink activeClassName="active" exact={true} className="link" to="/">Awesome</NavLink>
        <NavLink activeClassName="active" exact={true} className="link" to="/resources">Resources</NavLink>
        <NavLink activeClassName="active" exact={true} className="link" to="/kugs">Kotlin User Groups</NavLink>
      </div>
    </section>
  );
}

export interface Props {
}
