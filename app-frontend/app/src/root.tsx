import * as React from "react";
import {Suspense, lazy} from "react";
import {Route, BrowserRouter} from "react-router-dom";
import {Switch} from "react-router";

const Home = lazy(() => import("./pages/home/home"));
const Resources = lazy(() => import("./pages/resources/resources"));
const Kugs = lazy(() => import("./pages/kugs/kugs"));
const Articles = lazy(() => import("./pages/articles/articles"));

export function Root() {
  return (
    <BrowserRouter>
      <Suspense fallback={<div/>}>
        <Switch>
          <Route exact path="/" component={Home}/>
          <Route path="/kugs" component={Kugs}/>
          <Route path="/resources" component={Resources}/>
          <Route path="/articles" component={Articles}/>
        </Switch>
      </Suspense>
    </BrowserRouter>
  );
}
