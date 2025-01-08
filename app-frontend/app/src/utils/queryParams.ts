export const parseQueryParams = (search: string): Record<string, string> => {
  return Object.fromEntries(new URLSearchParams(search).entries());
};

export const stringifyQueryParams = (params: Record<string, string>): string => {
  return new URLSearchParams(params).toString();
};
