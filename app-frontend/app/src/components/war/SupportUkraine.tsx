import * as React from "react";
import "./SupportUkraine.scss";
import {memo} from "react";

function SupportUkraine() {
  return (
    <div className="support-ukraine-bar">
      <p>
        <a href="https://war.ukraine.ua/" target="_blank">Support Ukraine! 🇺🇦</a>
      </p>
    </div>
  );
}

export default memo(SupportUkraine)
