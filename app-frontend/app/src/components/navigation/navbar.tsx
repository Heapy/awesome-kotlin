import * as React from "react";
import NavLink from "../NavLink";
import {useState} from "react";
import ThemeSwitch from "../ThemeSwitch";

type NavbarLink = {
  name: string;
  href: string;
  title?: string;
};

type NavbarProps = {
  links: NavbarLink[];
};

function Navbar(props: NavbarProps) {
  const [isActive, setIsActive] = useState(false);

  function toggleBurgerMenu() {
    setIsActive(!isActive);
  }

  return (
    <nav className="navbar" role="navigation" aria-label="main navigation">
      <div className="navbar-brand">
        <NavLink to={"/"} className="navbar-item">
          <img src={require("../../../icons/favicon.svg")} alt="Logo" width="112" height="28"/>
        </NavLink>

        <a
          role="button"
          className={`navbar-burger ${isActive ? 'is-active' : ''}`}
          aria-label="menu"
          aria-expanded="false"
          onClick={toggleBurgerMenu}
        >
          <span aria-hidden="true"></span>
          <span aria-hidden="true"></span>
          <span aria-hidden="true"></span>
        </a>
      </div>

      <div className={`navbar-menu ${isActive ? 'is-active' : ''}`}>
        <div className="navbar-start">
          {props.links.map(link => (
            <NavLink key={link.href} to={link.href} title={link.title} className="navbar-item">
              {link.name}
            </NavLink>
          ))}
        </div>

        <div className="navbar-end">
          <div className="navbar-item">
            <div className="buttons">
              <NavLink to={"/login"} className="button is-light">
                Log in
              </NavLink>
              <ThemeSwitch />
            </div>
          </div>
        </div>
      </div>
    </nav>
  );
}

export default React.memo(Navbar);
