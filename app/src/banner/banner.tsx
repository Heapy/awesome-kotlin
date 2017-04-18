import * as React from "react";

const styles = require("./banner.less");

export function Banner() {
    return (
        <a className={styles.conference_banner}
           href="https://kotlinconf.com/?utm_source=kotlinlink&utm_medium=web">
            <div className={styles.conference_banner__img}/>
        </a>
    );
}