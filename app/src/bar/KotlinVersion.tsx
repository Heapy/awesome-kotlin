import * as React from "react";
import * as copy from "copy-to-clipboard";
import {style} from "typestyle";

const styles = {
  version: style({
    fontFamily: "monospace",
    fontSize: "20px",
    padding: "10px",
    margin: "10px",
    display: "inline-block",
    backgroundColor: "rgba(250, 250, 250, 0.7)",
    $nest: {
      "&:hover": {
        backgroundColor: "rgba(255, 255, 255, 0.7)",
      }
    }
  })
};

function copyVersion(version: string): () => void {
  return () => copy(version);
}

export function Component({version}: Props) {
  return (
    <span className={styles.version}
          title="Click to copy."
          onClick={copyVersion(version)}>
        {version}
      </span>
  );
}

export interface Props {
  readonly version: string
}
