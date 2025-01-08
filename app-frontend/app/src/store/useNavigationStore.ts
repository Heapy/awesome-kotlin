import {create} from "zustand";
import { immer } from "zustand/middleware/immer";

interface NavigationState {
  history: string[];
  currentPage: string;
  queryParams: Record<string, string>;
  push: (path: string, queryParams?: Record<string, string>) => void;
  replace: (path: string, queryParams?: Record<string, string>) => void;
  pop: () => void;
  setPage: (path: string, queryParams?: Record<string, string>) => void;
  setQueryParam: (key: string, value: string) => void;
  initialize: () => void;
}

const useNavigationStore = create(
  immer<NavigationState>((set) => ({
    history: [],
    currentPage: "/",
    queryParams: {},

    push: (path: string, queryParams: Record<string, string> = {}) =>
      set((state) => {
        const queryString = new URLSearchParams(queryParams).toString();
        const fullPath = queryString ? `${path}?${queryString}` : path;
        state.history.push(state.currentPage);
        state.currentPage = path;
        state.queryParams = queryParams;
        window.history.pushState({}, "", fullPath);
      }),

    replace: (path: string, queryParams: Record<string, string> = {}) =>
      set((state) => {
        const queryString = new URLSearchParams(queryParams).toString();
        const fullPath = queryString ? `${path}?${queryString}` : path;
        state.currentPage = path;
        state.queryParams = queryParams;
        window.history.replaceState({}, "", fullPath);
      }),

    pop: () =>
      set((state) => {
        const lastPage = state.history.pop();
        state.currentPage = lastPage || "/";
        state.queryParams = {};
        window.history.back();
      }),

    setPage: (path: string, queryParams: Record<string, string> = {}) =>
      set((state) => {
        state.currentPage = path;
        state.queryParams = queryParams;
      }),

    setQueryParam: (key: string, value: string) =>
      set((state) => {
        state.queryParams[key] = value;
        const queryString = new URLSearchParams(state.queryParams).toString();
        const fullPath = `${state.currentPage}?${queryString}`;
        window.history.replaceState({}, "", fullPath);
      }),

    initialize: () => {
      set((state) => {
        const path = window.location.pathname;
        const queryParams = Object.fromEntries(new URLSearchParams(window.location.search).entries());
        state.currentPage = path;
        state.queryParams = queryParams;
      });
    },
  }))
);

export default useNavigationStore;
