import * as React from "react";
import "./bar.scss";
import {useVersionsStore} from "./useVersionsStore";
import {memo, useEffect} from "react";
import KotlinVersion from "../version/kotlin_version";

function Bar() {
  const fetchVersions = useVersionsStore(state => state.fetchVersions);
  const versions = useVersionsStore(state => state.versions);

  useEffect(() => {
    fetchVersions();
  }, [fetchVersions]);

  return (
    <section className="bar">
      <div className="bar_wrapper">
        {versions.map(version => <KotlinVersion key={version} version={version}/>)}
      </div>
    </section>
  )
}

export default memo(Bar);
