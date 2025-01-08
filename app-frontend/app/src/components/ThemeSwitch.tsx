import * as React from "react";
import {useThemeStore} from "../store/themeStore";
import {useEffect} from "react";

const ThemeSwitch: React.FC = () => {
  const {theme, setTheme} = useThemeStore();

  useEffect(() => {
    setTheme(theme);
  }, [theme, setTheme]);

  const handleThemeChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setTheme(event.target.value as "light" | "dark" | "system");
  };

  return (
    <div className="navbar-item">
      <div className="select">
        <select value={theme} onChange={handleThemeChange}>
          <option value="light">Light</option>
          <option value="dark">Dark</option>
          <option value="system">System</option>
        </select>
      </div>
    </div>
  );
};

export default ThemeSwitch;
