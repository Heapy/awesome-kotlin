import {create} from "zustand";

interface VersionsState {
  versions: string[];
  fetchVersions: () => void;
}

export const useVersionsStore = create<VersionsState>((set) => ({
  versions: [],
  fetchVersions: async () => {
    const response = await fetch("/api/kotlin-versions");
    const data = await response.json();
    set({ versions: data });
  },
}));
