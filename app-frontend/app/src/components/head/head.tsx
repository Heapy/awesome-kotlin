import * as React from "react";
import {Logo, LOGOS} from "./logos";
import "./head.less";
import {useState} from "react";

function getLogos(): Logo[] {
  const activeLogos = LOGOS.filter(it => it.show());
  const exclusiveLogo = activeLogos.find(logo => logo.exclusive);

  if (exclusiveLogo) {
    return [exclusiveLogo]
  } else {
    return activeLogos
  }
}

export function Header() {
  const [logos] = useState(() => getLogos());
  const [index, setIndex] = useState(() => 0);

  function changeLogo() {
    if (logos.length <= 1) {
      return;
    }

    setIndex((index + 1) % logos.length);
  }

  function activeLogo() {
    const logo = logos[index];

    if (logo.link) {
      return (
        <a href={logo.link} target="_blank">
          <img src={logo.src}
               alt={logo.alt()}
               title={logo.alt()}
               className="head_logo"/>
        </a>
      );
    } else {
      return (
        <img src={logo.src}
             alt={logo.alt()}
             title={logo.alt()}
             className="head_logo"/>
      );
    }
  }

  return (
    <section className="head">
      <div className="head_wrapper" onClick={changeLogo}>
        {activeLogo()}
      </div>
    </section>
  );
}
