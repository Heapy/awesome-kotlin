import * as React from "react";
import LinksPage, {PageType} from "../../components/page_wrapper/page_wrapper";
import {memo} from "react";

function Resources() {
  return <LinksPage pageType={PageType.ALL}/>
}

export default memo(Resources);
