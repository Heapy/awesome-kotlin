import * as React from "react";
import * as ReactDOM from "react-dom";
import {Root} from "./root";
import "core-js/stable";
import "regenerator-runtime/runtime";
import "./style.less";
import "css-loader!normalize.css";

ReactDOM.render(
  <Root/>,
  document.getElementById("root")
);

