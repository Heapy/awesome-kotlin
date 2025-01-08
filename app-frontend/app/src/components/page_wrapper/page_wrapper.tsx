import * as React from "react";
import {memo, useEffect, useState} from "react";
import {Search} from "../search/search";
import Category from "../category/category";
import {Links} from "../../model";
import "./page_wrapper.scss";
import {useLinksStore} from "../../store/useLinksStore";
import SupportUkraine from "../war/SupportUkraine";
import DefaultNavbar from "../navigation/navbar_default";
import Bar from "../bar/bar";
import Header from "../head/head";
import Footer from "../footer/Footer";

function LinksPage(props: PageProps) {
  const [query, setQuery] = useState(null);
  const fetchLinks = useLinksStore(state => state.fetchLinks);
  const links: Links = useLinksStore(state => state.links);

  useEffect(() => {
    let params;
    if (props.pageType === PageType.AWESOME) {
      params = { awesome: "true", query }
    } else if (props.pageType === PageType.KUGS) {
      params = { kugs: "true", query }
    } else {
      params = { query }
    }
    const controller = new AbortController();
    fetchLinks(controller, params);
    return () => controller.abort();
  }, [fetchLinks, props.pageType, query]);

  return (
    <>
      <SupportUkraine/>
      <DefaultNavbar/>

      <Header/>

      <Search onChange={setQuery}/>

      <Bar/>

      <div className="wrapper">
        <header className="top-bar">
          <div className="container">
            <h1>Top Bar</h1>
          </div>
        </header>

        <div className="content">
          <div className="container">
            <aside className="sidebar">
              <h2>Sidebar</h2>
            </aside>
            <main className="main-content">
              <h2>Main Content</h2>
            </main>
          </div>
        </div>

        <footer className="footer">
          <div className="container">
            <div className="footer-column">Footer Column 1</div>
            <div className="footer-column">Footer Column 2</div>
            <div className="footer-column">Footer Column 3</div>
          </div>
        </footer>
      </div>

      {links.map((category, i) => {
        return <Category category={category} key={i}/>;
      })}

      <Footer/>
    </>
  );
}

export enum PageType {
  ALL = "all",
  AWESOME = "awesome",
  KUGS = "kugs"
}

interface PageProps {
  readonly pageType: PageType;
}

export default memo(LinksPage);
