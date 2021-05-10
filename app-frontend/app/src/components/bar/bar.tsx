import * as React from "react";
import {KotlinVersion} from "../version/KotlinVersion";
import "./bar.less";

export function Bar({versions}: Props) {
  return (
    <section className="bar">
      <div className="bar_wrapper">
        {versions.map(version => <KotlinVersion key={version} version={version}/>)}
      </div>
    </section>
  )
}

export interface Props {
  readonly versions: string[];
}
