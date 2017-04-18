import * as React from "react";

const styles = require("./banner.less");

export function Banner() {
    return (
        <a className={styles.conference_banner} href="https://kotlinconf.com">
            <div className={styles.conference_banner__img}/>
        </a>
    );
}