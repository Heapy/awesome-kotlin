export interface Subcategory {
  readonly name: string;
  readonly links: Link[];
}

export interface Link {
  readonly archived: boolean;
  readonly unsupported: boolean;
  readonly star: number;
  readonly href: string;
  readonly name: string;
  readonly update: string;
  readonly desc: string;
}
