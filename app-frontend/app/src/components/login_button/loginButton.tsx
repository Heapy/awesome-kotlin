import * as React from "react";
import {memo} from "react";
import "./loginButton.scss";

function LoginButton() {
  return (
    <div className="login-button-container">
      <a className="github-login-button" href="/auth/github/redirect">
        <span className="github-icon">
          <img aria-hidden="true" src={require("./github-logo.svg")} alt="GitHub Logo"/>
        </span>
        <span className="button-text">Continue with GitHub</span>
      </a>
    </div>
  );
}

export default memo(LoginButton);
