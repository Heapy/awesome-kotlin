import {create} from "zustand";

type Theme = "light" | "dark" | "system";

interface ThemeStore {
  theme: Theme;
  setTheme: (theme: Theme) => void;
}

export const useThemeStore = create<ThemeStore>((set) => ({
  theme: localStorage.getItem("theme") as Theme || "system",
  setTheme: (theme: Theme) => {
    localStorage.setItem("theme", theme);
    set({ theme });

    const root = document.documentElement;
    if (theme === "system") {
      const systemPreference = window.matchMedia("(prefers-color-scheme: dark)").matches ? "dark" : "light";
      root.setAttribute("data-theme", systemPreference);
    } else {
      root.setAttribute("data-theme", theme);
    }
  },
}));
