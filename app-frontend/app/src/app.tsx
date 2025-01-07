import * as React from "react";
import {lazy, memo, Suspense} from "react";
import useNavigationStore from "./store/useNavigationStore";
import Loading from "./components/loading/loading";
import LinksPage from "./pages/links/LinksPage";

const HomePage = lazy(() => import("./pages/home/HomePage"));
const ArticlesPage = lazy(() => import("./pages/articles/ArticlesPage"));
const NotFoundPage = lazy(() => import("./pages/not_found/NotFoundPage"));

const App: React.FC = () => {
  const currentPage = useNavigationStore((state) => state.currentPage);

  return (
    <>
      <Suspense fallback={<Loading />}>
        {(() => {
          switch (currentPage) {
            case "/":
              return <HomePage/>;
            // keep kugs for backward compatibility
            case "/kugs":
            case "/resources":
              return <LinksPage/>;
            case "/articles":
              return <ArticlesPage/>;
            default:
              return <NotFoundPage/>;
          }
        })()}
      </Suspense>
    </>
  )
};

export default memo(App);
