import * as React from "react";
import {LinksPageComponent} from "../../components/page_wrapper/page_wrapper";
import {kugs} from "../../links";

export default function Kugs() {
  return <LinksPageComponent displayLinks={kugs} searchLinks={kugs}/>
}
