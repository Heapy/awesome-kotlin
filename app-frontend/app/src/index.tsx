import * as React from "react";
import {createRoot} from "react-dom/client";
import "./style.scss";
import useNavigationStore from "./store/useNavigationStore";
import {parseQueryParams} from "./utils/queryParams";
import App from "./app";

useNavigationStore.getState().initialize();

window.addEventListener("popstate", () => {
  const path = window.location.pathname;
  const queryParams = parseQueryParams(window.location.search);
  useNavigationStore.getState().setPage(path, queryParams);
});

const socket = new WebSocket("/api/ws");

// Connection opened
socket.onopen = () => {
  console.log("WebSocket connection established");
};

// Listen for messages
socket.onmessage = (event) => {
  const message = event.data;
  console.log("Message from server: ", message);

  // Check if the message is the specific text
  if (message === "reload") {
    // Reload the page
    window.location.reload();
  }
};

// Connection closed or errors
socket.onclose = () => {
  console.log("WebSocket connection closed");
};

socket.onerror = (error) => {
  console.info("WebSocket error: ", error);
};

createRoot(document.getElementById("root"))
  .render(
    <React.StrictMode>
      <App/>
    </React.StrictMode>
  );

