import * as React from "react";

function Footer() {
  return (
    <footer className="footer">
      <div className="content has-text-centered">
        <p>
          <strong>Bulma Login Page</strong> by <a href="https://yourwebsite.com">Your Name</a>. The source code is
          licensed
          <a href="https://opensource.org/licenses/mit-license.php">MIT</a>. The website content is licensed{" "}
          <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY NC SA 4.0</a>.
        </p>
      </div>
    </footer>
  );
}

export default React.memo(Footer);
