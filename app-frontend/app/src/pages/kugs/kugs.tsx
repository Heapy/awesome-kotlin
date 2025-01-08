import * as React from "react";
import LinksPage, {PageType} from "../../components/page_wrapper/page_wrapper";
import {memo} from "react";

function KugsPage() {
  return <LinksPage pageType={PageType.KUGS}/>
}

export default memo(KugsPage);
