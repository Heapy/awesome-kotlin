import * as React from "react";
import {memo} from "react";
import DefaultNavbar from "../../components/navigation/navbar_default";
import Footer from "../../components/footer/Footer";

const TheDailyKotlinPage: React.FC = () => {
  return (
    <>
      <DefaultNavbar/>

      <div>TBD</div>

      <Footer/>
    </>
  );
}

export default memo(TheDailyKotlinPage);
