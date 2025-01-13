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
}

const NavLink: React.FC<PropsWithChildren<NavLinkProps>> = (
  {
    to,
    queryParams = {},
    children,
    activeClassName = "active",
    className = "",
    title,
  }
) => {
  const {currentPage, queryParams: currentQueryParams, push} = useNavigationStore(useShallow((state) => ({
    currentPage: state.currentPage,
    queryParams: state.queryParams,
    push: state.push,
  })));

  const handleClick = (event: React.MouseEvent<HTMLAnchorElement, MouseEvent>) => {
    event.preventDefault();
    if (currentPage !== to || JSON.stringify(currentQueryParams) !== JSON.stringify(queryParams)) {
      push(to, queryParams);
    }
  };

  const isActive = currentPage === to && JSON.stringify(currentQueryParams) === JSON.stringify(queryParams);
  const linkClassName = isActive ? `${className} ${activeClassName}` : className;

  return (
    <a href={to} className={linkClassName} onClick={handleClick} title={title}>
      {children}
    </a>
  );
};

export default NavLink;
