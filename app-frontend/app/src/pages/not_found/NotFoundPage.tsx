import * as React from "react";
import Footer from "../../components/footer/Footer";
import DefaultNavbar from "../../components/navigation/navbar_default";
import NavLink from "../../components/NavLink";
import {memo} from "react";
import "./NotFoundPage.scss";

function NotFoundPage() {
  return (
    <>
      <DefaultNavbar/>

      <section className="not-found-container">
        <div className="not-found-content">
          <div className="not-found-illustration">
            <div className="error-code">404</div>
            <div className="error-icon">
              <svg width="120" height="120" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.5">
                <circle cx="12" cy="12" r="10"/>
                <path d="M12 8v4"/>
                <path d="M12 16h.01"/>
              </svg>
            </div>
          </div>
          
          <div className="not-found-text">
            <h1 className="error-title">Page Not Found</h1>
            <p className="error-description">
              Oops! The page you're looking for doesn't exist. It might have been moved, 
              deleted, or you entered the wrong URL.
            </p>
          </div>
          
          <div className="not-found-actions">
            <NavLink to="/" className="primary-button">
              <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"/>
              </svg>
              Go Home
            </NavLink>
            
            <a 
              href="https://github.com/Heapy/awesome-kotlin/issues/new" 
              target="_blank" 
              rel="noopener noreferrer"
              className="secondary-button"
            >
              <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/>
                <polyline points="14,2 14,8 20,8"/>
                <line x1="16" y1="13" x2="8" y2="13"/>
                <line x1="16" y1="17" x2="8" y2="17"/>
                <polyline points="10,9 9,9 8,9"/>
              </svg>
              Report Issue
            </a>
          </div>
          
          <div className="helpful-links">
            <h3>Popular Pages</h3>
            <div className="link-grid">
              <NavLink to="/" className="help-link">
                <span>🏠</span>
                <span>Home</span>
              </NavLink>
            </div>
          </div>
        </div>
      </section>

      <Footer/>
    </>
  );
}

export default memo(NotFoundPage);
