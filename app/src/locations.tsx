export function searchString(object: QueryParams): string {
  return "?" + Object.keys(object).map(key => `${key}=${encodeURIComponent(object[key])}`).join("&")
}

interface QueryParams {
  [key: string]: string
}
