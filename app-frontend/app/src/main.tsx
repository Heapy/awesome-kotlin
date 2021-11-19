import * as React from "react";
import * as ReactDOM from "react-dom";
import {Root} from "./root";
import "core-js/stable";
import "./style.less";
import "css-loader!normalize.css";

ReactDOM.render(
  <React.StrictMode>
    <Root />
  </React.StrictMode>,
  document.getElementById("root")
);

