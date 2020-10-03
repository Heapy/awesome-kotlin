import {Links} from "./model";

const data: Links = require("../links.json");

export function LinksData(): Links {
  return data.filter(it =>  it.name != "Kotlin User Groups")
}

export function KugsData(): Links {
  return data.filter(it =>  it.name == "Kotlin User Groups")
}
