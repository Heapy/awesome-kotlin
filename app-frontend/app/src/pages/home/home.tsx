import * as React from "react";
import LinksPage, {PageType} from "../../components/page_wrapper/page_wrapper";
import {memo} from "react";

function HomePage() {
  return <LinksPage pageType={PageType.AWESOME}/>
}

export default memo(HomePage);
