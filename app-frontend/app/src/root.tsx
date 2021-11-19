import * as React from "react";
import {Suspense, lazy} from "react";
import {BrowserRouter, Routes, Route} from "react-router-dom";

const Home = lazy(() => import("./pages/home/home"));
const Resources = lazy(() => import("./pages/resources/resources"));
const Kugs = lazy(() => import("./pages/kugs/kugs"));
const Articles = lazy(() => import("./pages/articles/articles"));

export function Root() {
  return (
    <BrowserRouter>
      <Suspense fallback={<div/>}>
        <Routes>
          <Route path="/" element={<Home/>}/>
          <Route path="/kugs" element={<Kugs/>}/>
          <Route path="/resources" element={<Resources/>}/>
          <Route path="/articles" element={<Articles/>}/>
        </Routes>
      </Suspense>
    </BrowserRouter>
  );
}
