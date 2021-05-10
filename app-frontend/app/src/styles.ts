export function classes(classes: {[className: string]: boolean}) {
  return Object.keys(classes)
    .filter(key => !!classes[key])
    .join(" ");
}
