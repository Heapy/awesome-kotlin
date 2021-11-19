import * as React from "react";
import {NavLink} from "react-router-dom";
import "./Navigation.less"

export function Navigation(props: Props) {
  return (
    <section className="navigation_bar">
      <div className="navigation_bar_wrapper">
        <NavLink
          end={true}
          className={({isActive}) => "link" + (isActive ? " active" : "")}
          to="/">
          Awesome
        </NavLink>
        <NavLink
          end={true}
          className={({isActive}) => "link" + (isActive ? " active" : "")}
          to="/resources">
          Resources
        </NavLink>
        <NavLink
          end={true}
          className={({isActive}) => "link" + (isActive ? " active" : "")}
          to="/kugs">
          Kotlin User Groups
        </NavLink>
      </div>
    </section>
  );
}

export interface Props {
}
