import * as React from "react";
import {LinksPageComponent} from "../../components/page_wrapper/page_wrapper";
import {all, awesome} from "../../links";

export default function Home() {
  return <LinksPageComponent displayLinks={awesome} searchLinks={all}/>
}
