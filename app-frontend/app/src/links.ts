import {Category, Links, LinkState} from "./model";

const data: Links = (require("../links.json") as Links).map(sortLinks);
export const kugs: Links = data.filter(it => it.name == "Kotlin User Groups")
export const all: Links = data.filter(it => it.name != "Kotlin User Groups")
export const awesome: Links = all.map(awesomeLinks)
  .filter(category => category.subcategories.length > 0)
  .map(sortLinks);

function sortLinks(category: Category): Category {
  return {
    name: category.name,
    subcategories: category.subcategories.sort((a, b) => {
      return b.links.length - a.links.length
    })
  }
}

function awesomeLinks(category: Category): Category {
  return {
    name: category.name,
    subcategories: category.subcategories
      .map(subcategory => {
        return {
          name: subcategory.name,
          links: subcategory.links.filter(link => link.state === LinkState.AWESOME)
        };
      })
      .filter(subcategory => subcategory.links.length > 0)
  }
}
