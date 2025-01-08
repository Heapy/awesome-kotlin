import * as React from "react";
import {lazy, memo, Suspense} from "react";
import useNavigationStore from "./store/useNavigationStore";

const HomePage = lazy(() => import("./pages/home/home"));
const ResourcesPage = lazy(() => import("./pages/resources/resources"));
const KugsPage = lazy(() => import("./pages/kugs/kugs"));
const ArticlesPage = lazy(() => import("./pages/articles/articles"));
const LoginPage = lazy(() => import("./pages/login/login"));
const NotFoundPage = lazy(() => import("./pages/404/404"));

const App: React.FC = () => {
  const currentPage = useNavigationStore((state) => state.currentPage);

  return (
    <Suspense fallback={<div>Loading...</div>}>
      {(() => {
        switch (currentPage) {
          case "/":
            return <HomePage/>;
          case "/login":
            return <LoginPage/>;
          case "/articles":
            return <ArticlesPage/>;
          case "/kugs":
            return <KugsPage/>;
          case "/resources":
            return <ResourcesPage/>;
          default:
            return <NotFoundPage/>;
        }
      })()}
    </Suspense>
  )
};

export default memo(App);
