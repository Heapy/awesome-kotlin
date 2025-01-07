export const parseQueryParams = (search: string): Record<string, string> => {
  return Object.fromEntries(new URLSearchParams(search).entries());
};
