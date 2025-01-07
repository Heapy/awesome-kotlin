import * as React from "react";
import {useThemeStore} from "../store/themeStore";
import {useEffect} from "react";

const ThemeSwitch: React.FC = () => {
  const {theme, setTheme} = useThemeStore();

  useEffect(() => {
    setTheme(theme);
  }, [theme, setTheme]);

  const handleThemeToggle = () => {
    const themes: ("light" | "dark" | "system")[] = ["light", "dark", "system"];
    const currentIndex = themes.indexOf(theme);
    const nextIndex = (currentIndex + 1) % themes.length;
    setTheme(themes[nextIndex]);
  };

  const getIcon = (themeType: "light" | "dark" | "system") => {
    switch (themeType) {
      case "light":
        return (
          <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
            <circle cx="12" cy="12" r="5"/>
            <path d="M12 1v2M12 21v2M4.22 4.22l1.42 1.42M18.36 18.36l1.42 1.42M1 12h2M21 12h2M4.22 19.78l1.42-1.42M18.36 5.64l1.42-1.42"/>
          </svg>
        );
      case "dark":
        return (
          <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
            <path d="M21 12.79A9 9 0 1 1 11.21 3 7 7 0 0 0 21 12.79z"/>
          </svg>
        );
      case "system":
        return (
          <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
            <circle cx="12" cy="12" r="9"/>
            <path d="M9 15L12 8L15 15M10.5 13H13.5" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
          </svg>
        );
    }
  };

  const getThemeTitle = (themeType: "light" | "dark" | "system") => {
    switch (themeType) {
      case "light":
        return "Switch to dark theme";
      case "dark":
        return "Switch to system theme";
      case "system":
        return "Switch to light theme";
    }
  };

  return (
    <button
      className="theme-toggle"
      onClick={handleThemeToggle}
      title={getThemeTitle(theme)}
    >
      {getIcon(theme)}
    </button>
  );
};

export default ThemeSwitch;
