import * as React from "react";
import {LinksPageComponent} from "../../components/page_wrapper/page_wrapper";
import {all} from "../../links";

export default function Resources() {
  return <LinksPageComponent displayLinks={all} searchLinks={all}/>
}
