import * as React from "react";
import LinksPageComponent from "../../components/page_wrapper/page_wrapper";
import {LinksData} from "../../links";

export default function Home() {
  return <LinksPageComponent data={LinksData()}/>
}
