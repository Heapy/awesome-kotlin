import {Category} from "./model";

const data: Category[] = require("../links.json");

export function LinksData(): Category[] {
  return data.filter(it =>  it.name != "Kotlin User Groups")
}

export function KugsData(): Category[] {
  return data.filter(it =>  it.name == "Kotlin User Groups")
}
