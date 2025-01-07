import * as React from "react";
import { memo } from "react";
import "./loading.scss";

const Loading: React.FC = () => {
  return (
    <div className="loading-container">
      <div className="loading-spinner"></div>
      <div className="loading-text">Loading...</div>
    </div>
  );
};

export default memo(Loading);