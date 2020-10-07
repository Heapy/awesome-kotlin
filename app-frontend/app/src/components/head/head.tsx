import * as React from "react";
import {Logo, LOGOS} from "./logos";

const styles = require("./head.less");

interface State {
  readonly index: number;
  readonly logos: Logo[];
}

export class Head extends React.Component<{}, State> {
  constructor(props) {
    super(props);

    const activeLogos = LOGOS.filter(it => it.show())
    const exclusiveLogo = activeLogos.find(logo => logo.exclusive)
    let logos: Logo[]

    if (exclusiveLogo) {
      logos = [exclusiveLogo]
    } else {
      logos = activeLogos
    }

    this.state = {
      index: 0,
      logos: logos
    };
  }

  changeLogo = () => {
    if (this.state.logos.length <= 1) {
      return;
    }

    this.setState({
      index: (this.state.index + 1) % this.state.logos.length
    });
  };

  activeLogo = () => {
    const logo = this.state.logos[this.state.index];

    if (logo.link) {
      return (
        <a href={logo.link} target="_blank">
          <img src={logo.src}
               alt={logo.alt()}
               className={styles.head_logo}/>
        </a>
      );
    } else {
      return (
        <img src={logo.src}
             alt={logo.alt()}
             className={styles.head_logo}/>
      );
    }
  }

  render() {
    return (
      <section className={styles.head}>
        <div className={styles.head_wrapper} onClick={this.changeLogo}>
          {this.activeLogo()}
        </div>
      </section>
    );
  }
}
