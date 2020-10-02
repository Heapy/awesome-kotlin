import * as React from "react";
import {Home} from "./pages/home/home";
import {Route, BrowserRouter} from "react-router-dom";
import {Switch} from "react-router";
import {Kugs} from "./pages/kugs/kugs";
import {Articles} from "./pages/articles/articles";

export function Root() {
  return (
    <BrowserRouter>
      <Switch>
        <Route exact path="/" component={Home}/>
        <Route path="/articles" component={Articles}/>
        <Route path="/kugs" component={Kugs}/>
      </Switch>
    </BrowserRouter>
  );
}
