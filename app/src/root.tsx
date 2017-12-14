import * as React from "react";
import {Page} from "./page/page";
import {Articles} from "./articles/articles";
import {Route, BrowserRouter} from "react-router-dom";
import {Switch} from "react-router";

export function Root() {
  return (
    <BrowserRouter>
      <Switch>
        <Route exact path="/" component={Page}/>
        <Route path="/articles" component={Articles}/>
      </Switch>
    </BrowserRouter>
  );
}
