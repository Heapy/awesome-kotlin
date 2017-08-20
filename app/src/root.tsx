import * as React from "react";
import {Page} from "./page/page";
import {Articles} from "./articles/articles";
import {Router, Route, browserHistory} from "react-router";

export function Root() {
  return (
    <Router history={browserHistory}>
      <Route path={"/"} component={Page}/>
      <Route path={"/articles"} component={Articles}/>
    </Router>
  );
}
