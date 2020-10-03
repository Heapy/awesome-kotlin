import * as React from "react";
import {style} from "typestyle";
import {NavLink} from "react-router-dom";

const {wrapper, bar} = {
  wrapper: style({
    display: "block",
    maxWidth: "1024px",
    margin: "auto",
    padding: "20px",
    $nest: {
      "a.link": {
        fontSize: "24px",
        color: "#F0610B",
        textDecoration: "none",
        margin: "10px",

        $nest: {
          "&.active": {
            textDecoration: "underline"
          }
        }
      }
    }
  }),
  bar: style({
    backgroundColor: "rgba(242, 242, 242, 0.7)",
    $nest: {
      "& > *": {
        boxSizing: "border-box"
      }
    }
  })
};

export function Navigation(props: Props) {
  return (
    <section className={bar}>
      <div className={wrapper}>
        <NavLink activeClassName="active" exact={true} className="link" to="/">Home</NavLink>
        <NavLink activeClassName="active" exact={true} className="link" to="/kugs">Kotlin User Groups</NavLink>
      </div>
    </section>
  );
}

export interface Props {
}
