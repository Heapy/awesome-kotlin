import * as React from "react";
import * as copy from "copy-to-clipboard";
import "./KotlinVersion.less";

function copyVersion(version: string): () => void {
  return () => copy(version);
}

export function KotlinVersion({version}: Props) {
  return (
    <span className="kotlin_version"
          title="Click to copy."
          onClick={copyVersion(version)}>
        {version}
      </span>
  );
}

export interface Props {
  readonly version: string
}
