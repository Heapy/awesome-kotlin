import * as React from "react";
import "./KotlinVersionBar.scss";
import {useVersionsStore} from "./useVersionsStore";
import {memo, useEffect} from "react";
import KotlinVersion from "../version/kotlin_version";

function KotlinVersionBar() {
  const fetchVersions = useVersionsStore(state => state.fetch);
  const versions = useVersionsStore(state => state.data);

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

export default memo(KotlinVersionBar);
