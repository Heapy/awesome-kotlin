import * as React from "react";
import SupportUkraine from "../../components/war/SupportUkraine";
import Footer from "../../components/footer/Footer";
import DefaultNavbar from "../../components/navigation/navbar_default";
import {memo} from "react";
import "./404.scss";

function NotFoundPage() {
  return (
    <>
      <SupportUkraine/>
      <DefaultNavbar/>
      <section className="hero is-fullheight-with-navbar">
        <div className="hero-body">
          <div className="container">
            <div className="columns is-centered">
              <div className="column is-8">
                <div className="content">
                  <h1 className="title is-8 has-text-centered">
                    404 Page Not Found
                  </h1>
                  <p>An unexpected error has occurred. Please contact the site owner.</p>
                  <a className="button is-primary is-fullwidth"
                     href="https://github.com/Heapy/awesome-kotlin/issues/new" target="_blank">
                    Contact Us
                  </a>
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

export default memo(NotFoundPage);
