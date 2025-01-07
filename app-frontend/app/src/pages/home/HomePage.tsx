import * as React from "react";
import {memo} from "react";
import TheDailyKotlinPage from "../the_daily_kotlin/TheDailyKotlinPage";

function HomePage() {
  return <TheDailyKotlinPage/>;
}

export default memo(HomePage);
