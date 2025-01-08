import * as React from "react";
import SupportUkraine from "../../components/war/SupportUkraine";
import Footer from "../../components/footer/Footer";
import DefaultNavbar from "../../components/navigation/navbar_default";
import LoginButton from "../../components/login_button/loginButton";
import {memo} from "react";

function LoginPage() {
  return (
    <>
      <SupportUkraine/>
      <DefaultNavbar/>
      <section className="hero is-fullheight-with-navbar">
        <div className="hero-body">
          <div className="container">
            <div className="columns is-centered">
              <div className="column is-4">
                <div className="box">
                  <h1 className="title is-4 has-text-centered">
                    Login to Awesome Kotlin
                  </h1>
                  <LoginButton/>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
      <Footer/>
    </>
  );
}

export default memo(LoginPage);
