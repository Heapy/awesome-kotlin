interface Link {
  readonly name: string;
  readonly href: string;
  readonly desc?: string;
  readonly type?: 'github' | 'bitbucket' | 'blog' | 'kug';
  readonly tags?: string[];
  readonly whitelisted?: boolean;
}

interface Subcategory {
  readonly id?: string;
  readonly name: string;
  readonly links: Link[];
}

interface Category {
  readonly id?: string;
  readonly name: string;
  readonly subcategories: Subcategory[];
}

type ArticleFeature = 'mathjax';
type ArticleType = 'video' | 'slides' | 'webinar';

interface Article {
  readonly title: string;
  readonly url: string;
  readonly categories: string[];
  readonly features: ArticleFeature[];
  readonly author: string;
  readonly date: string;
  readonly description: string;
  readonly filename: string;
  readonly file: string; // input filename
  readonly type: ArticleType
  prev: string;
  next: string;
}
