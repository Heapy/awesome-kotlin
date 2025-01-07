import * as React from "react";
import {memo, useEffect, useState, useRef} from "react";
import {useUserStore} from "../../store/useUserStore";
import "./LoginComponent.scss";

function LoginComponent() {
  const { user, isLoading, logout, fetchUser } = useUserStore();
  const [showDropdown, setShowDropdown] = useState(false);
  const dropdownRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    fetchUser();
  }, [fetchUser]);

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target as Node)) {
        setShowDropdown(false);
      }
    };

    if (showDropdown) {
      document.addEventListener('mousedown', handleClickOutside);
    }

    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [showDropdown]);

  const handleLogout = () => {
    logout();
    setShowDropdown(false);
  };

  const toggleDropdown = () => {
    setShowDropdown(!showDropdown);
  };

  if (isLoading) {
    return (
      <div className="login-component">
        <div className="loading-spinner"></div>
      </div>
    );
  }

  if (user) {
    return (
      <div className="login-component">
        <div className="user-profile" ref={dropdownRef}>
          <button 
            className="user-avatar-button"
            onClick={toggleDropdown}
            title={`Logged in as ${user.username}`}
          >
            <img 
              src={user.avatar} 
              alt={`${user.username}'s avatar`}
              className="user-avatar"
            />
            <span className="user-name">{user.username}</span>
            <svg className="dropdown-arrow" width="12" height="12" viewBox="0 0 12 12">
              <path d="M3 4.5L6 7.5L9 4.5" stroke="currentColor" strokeWidth="1.5" fill="none" strokeLinecap="round" strokeLinejoin="round"/>
            </svg>
          </button>
          
          {showDropdown && (
            <div className="user-dropdown">
              <div className="dropdown-content">
                <div className="user-info">
                  <img src={user.avatar} alt={`${user.username}'s avatar`} className="dropdown-avatar" />
                  <div className="user-details">
                    <span className="username">{user.username}</span>
                    {user.email && <span className="email">{user.email}</span>}
                  </div>
                </div>
                <div className="dropdown-divider"></div>
                <div className="dropdown-actions">
                  {user.profileUrl && (
                    <a 
                      href={user.profileUrl} 
                      target="_blank" 
                      rel="noopener noreferrer"
                      className="dropdown-link"
                    >
                      View Profile
                    </a>
                  )}
                  <button 
                    onClick={handleLogout}
                    className="dropdown-link logout-link"
                  >
                    Sign Out
                  </button>
                </div>
              </div>
            </div>
          )}
        </div>
      </div>
    );
  }

  return (
    <div className="login-component">
      <a className="login-button" href="/auth/github/redirect">
        <span className="github-icon">
          <svg width="16" height="16" viewBox="0 0 16 16" fill="currentColor">
            <path d="M8 0C3.58 0 0 3.58 0 8c0 3.54 2.29 6.53 5.47 7.59.4.07.55-.17.55-.38 0-.19-.01-.82-.01-1.49-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52-.01-.53.63-.01 1.08.58 1.23.82.72 1.21 1.87.87 2.33.66.07-.52.28-.87.51-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12 0 0 .67-.21 2.2.82.64-.18 1.32-.27 2-.27.68 0 1.36.09 2 .27 1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48 0 1.07-.01 1.93-.01 2.2 0 .21.15.46.55.38A8.013 8.013 0 0016 8c0-4.42-3.58-8-8-8z"/>
          </svg>
        </span>
        <span className="button-text">Continue with GitHub</span>
      </a>
    </div>
  );
}

export default memo(LoginComponent);