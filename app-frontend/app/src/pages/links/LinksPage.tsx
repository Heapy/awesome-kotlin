import * as React from "react";
import {memo, useEffect, useState} from "react";
import {Search} from "../../components/search/search";
import Category from "../../components/category/Category";
import {Links} from "../../model";
import "./LinksPage.scss";
import {useLinksStore} from "../../store/useLinksStore";
import DefaultNavbar from "../../components/navigation/navbar_default";
import KotlinVersionBar from "../../components/kotlin_version_bar/KotlinVersionBar";
import Header from "../../components/head/Head";
import Footer from "../../components/footer/Footer";

function LinksPage() {
  const [query, setQuery] = useState(null);
  const fetchLinks = useLinksStore(state => state.fetch);
  const cancel = useLinksStore(state => state.cancel);
  const links: Links = useLinksStore(state => state.data);

  useEffect(() => {
    fetchLinks({ query });
    return cancel;
  }, [fetchLinks, query, cancel]);

  return (
    <>
      <DefaultNavbar/>

      <Header/>

      <Search onChange={setQuery}/>

      <KotlinVersionBar/>

      {links.map((category) => {
        return <Category category={category} key={category.name}/>;
      })}

      <Footer/>
    </>
  );
}

export default memo(LinksPage);
