import * as React from "react";
import {PropsWithChildren} from "react";
import { useShallow } from "zustand/shallow";
import useNavigationStore from "../store/useNavigationStore";

interface NavLinkProps {
  to: string;
  queryParams?: Record<string, string>;
  activeClassName?: string;
  className?: string;
  title?: string;
  external?: boolean;
}

const NavLink: React.FC<PropsWithChildren<NavLinkProps>> = (
  {
    to,
    queryParams = {},
    children,
    activeClassName = "active",
    className = "",
    external = false,
    title,
  }
) => {
  const {currentPage, currentQueryParams, push} = useNavigationStore(useShallow((state) => ({
    currentPage: state.currentPage,
    currentQueryParams: state.queryParams,
    push: state.push,
  })));

  const handleClick = (event: React.MouseEvent<HTMLAnchorElement, MouseEvent>) => {
    event.preventDefault();
    if (currentPage !== to || JSON.stringify(currentQueryParams) !== JSON.stringify(queryParams)) {
      push(to, queryParams);
    }
  };

  const isActive = currentPage === to;
  const linkClassName = isActive ? `${className} ${activeClassName}` : className;

  if (external) {
    return (
      <a href={to} className={linkClassName} title={title} target="_blank" rel="noopener noreferrer">
        {children}
      </a>
    )
  } else {
    return (
      <a href={to} className={linkClassName} onClick={handleClick} title={title}>
        {children}
      </a>
    )
  }
};

export default NavLink;
