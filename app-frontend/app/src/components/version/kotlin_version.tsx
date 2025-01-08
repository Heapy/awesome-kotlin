import * as React from "react";
import * as copy from "copy-to-clipboard";
import "./kotlin_version.scss";
import {memo} from "react";

function copyVersion(version: string): () => void {
  return () => copy(version);
}

interface Props {
  readonly version: string
}

function KotlinVersion(props: Props) {
  const version = props.version;

  return (
    <span className="kotlin_version"
          title="Click to copy."
          onClick={copyVersion(version)}>
        {version}
      </span>
  );
}

export default memo(KotlinVersion);
