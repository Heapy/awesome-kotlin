import * as React from "react";
import {style} from "typestyle";
import {KotlinVersion} from "./KotlinVersion";

const {wrapper, bar} = {
  wrapper: style({
    display: "block",
    maxWidth: "1024px",
    margin: "auto",
    padding: "20px"
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

export function Bar({versions}: Props) {
  return (
    <section className={bar}>
      <div className={wrapper}>
        {versions.map(version => <KotlinVersion key={version} version={version}/>)}
      </div>
    </section>
  )
}

export interface Props {
  readonly versions: string[];
}
