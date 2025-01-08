import * as React from "react";
import {memo} from "react";

function LoginButton() {
  return (
    <div className="buttons is-centered">
      <a className="button is-link is-fullwidth" href="/auth/github/redirect">
        <span className="icon">
          <img aria-hidden="true" src={require("./github-logo.svg")} alt="GitHub Logo"/>
        </span>
        <span>Login with GitHub</span>
      </a>
    </div>
  );
}

export default memo(LoginButton);
