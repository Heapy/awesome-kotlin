import * as React from "react";
import Navbar from "./navbar";
import {memo} from "react";

function DefaultNavbar() {
  return (
    <Navbar links={[
      {
        name: "Essential",
        href: "/",
      },
      {
        name: "Everything",
        href: "/resources",
      },
      {
        name: "Kotlin User Groups",
        href: "/kugs",
      },
      {
        name: "Github",
        title: "Fork me!",
        href: "https://github.com/KotlinBy/awesome-kotlin",
      },
    ]}/>
  );
}

export default memo(DefaultNavbar);
