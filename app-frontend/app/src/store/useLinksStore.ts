import {Links} from "../model";
import {create} from "zustand";

interface LinksState {
  links: Links;
  fetchLinks: (controller: AbortController, queryParams?: Record<string, string>) => void;
}

export const useLinksStore = create<LinksState>((set) => ({
  links: [],
  fetchLinks: async (
    controller,
    queryParams = {}
  ) => {
    // remove "query" from queryParams if it's null or empty
    if (queryParams.query === null || queryParams.query === "") {
      delete queryParams.query;
    }
    const queryString = new URLSearchParams(queryParams).toString();
    const url = `/api/links${queryString ? `?${queryString}` : ""}`;

    try {
      const response = await fetch(url, {signal: controller.signal});
      const data = await response.json();
      set({links: data});
    } catch (e) {
      if (e.name !== "AbortError") {
        console.error("Failed to fetch links", e);
        throw e
      }
    }
  },
}));
