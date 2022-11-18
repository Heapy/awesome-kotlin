import * as React from "react";
import {createRoot} from 'react-dom/client';
import {Root} from "./root";
import "core-js/stable";
import "./style.less";
import "css-loader!normalize.css";

createRoot(document.getElementById("root"))
  .render(
    <React.StrictMode>
      <Root/>
    </React.StrictMode>
  );

