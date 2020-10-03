import * as React from "react";
import LinksPageComponent from "../../components/page_wrapper/page_wrapper";
import {KugsData} from "../../links";

export default function Kugs() {
  return <LinksPageComponent data={KugsData()}/>
}
