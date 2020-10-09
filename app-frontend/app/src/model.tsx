export type Links = Category[];

export interface Category {
  readonly name: string;
  readonly subcategories: Subcategory[];
}

export interface Subcategory {
  readonly name: string;
  readonly links: Link[];
}

export interface Link {
  readonly name: string;
  readonly href: string;
  readonly desc: string;
  readonly platforms: PlatformType[]
  readonly tags: string[]
  readonly star: number | null;
  readonly update: string | null;
  readonly state: LinkState;
}

export enum PlatformType {
  ANDROID = "ANDROID",
  COMMON = "COMMON",
  IOS = "IOS",
  JS = "JS",
  JVM = "JVM",
  NATIVE = "NATIVE",
  WASM = "WASM"
}

export enum LinkState {
  AWESOME = "AWESOME",
  UNSUPPORTED = "UNSUPPORTED",
  ARCHIVED = "ARCHIVED",
  DEFAULT = "DEFAULT"
}
