import * as React from "react";
import Navbar from "./navbar";
import {memo} from "react";

function DefaultNavbar() {
  return (
    <Navbar links={[
      {
        name: "The Daily Kotlin",
        href: "/",
      },
      {
        name: "Projects",
        href: "/resources",
      },
    ]}/>
  );
}

export default memo(DefaultNavbar);
