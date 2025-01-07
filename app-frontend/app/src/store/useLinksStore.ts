import {Links} from "../model";
import {createApiStore} from "../utils/createApiStore";

export const useLinksStore = createApiStore<Links>(
  {
    endpoint: "/api/links",
    initialData: [],
  }
);
