import * as React from "react";

const styles = require("./head.less");

export class Head extends React.Component<{}, State> {
  constructor(props) {
    super(props);
    this.state = {
      counter: 0,
      logos: logosProvider()
    };
  }

  changeLogo = () => {
    const {
      counter,
      logos
    } = this.state;

    // Skip if logo linked
    if (logos[counter].link) {
      return
    }

    this.setState({
      counter: (counter + 1) % logos.length
    });
  };

  render() {
    const {
      counter,
      logos
    } = this.state;

    return (
      <section className={styles.head}>
        <div className={styles.head_wrapper} onClick={this.changeLogo}>
          {getLogo(counter, logos)}
        </div>
      </section>
    );
  }
}

interface State {
  readonly counter: number;
  readonly logos: Logo[];
}

interface Logo {
  readonly src: string;
  readonly alt: string;
  readonly show: Predicate;
  readonly link?: string;
}

type Predicate = () => boolean;

function getLogo(index: number, logos: Logo[]) {
  const logo = logos[index];

  if (logo.link) {
    return (
      <a href={logo.link} target="_blank">
        <img src={logo.src}
             alt={logo.alt}
             className={styles.head_logo}/>
      </a>
    );
  } else {
    return (
      <img src={logo.src}
           alt={logo.alt}
           className={styles.head_logo}/>
    );
  }
}

function logosProvider(): Logo[] {
  return [{
    src: require("./kc2019.svg"),
    alt: "KotlinConf 2019 Live Streaming",
    show: () => true,
    link: "https://blog.jetbrains.com/kotlin/2019/11/kotlinconf-2019-live-join-the-broadcast-attend-the-qa/?utm_source=awesome_kotlin&utm_medium=referral&utm_campaign=kotlinconf-livestream"
  }, {
    src: require("./kotlin-force.svg"),
    alt: "Kotlin Language Logo",
    show: () => {
      const today = new Date();
      // May, 4
      return today.getMonth() === 4 && today.getDate() === 4;
    }
  }, {
    src: require("./kotlin-0.svg"),
    alt: "Kotlin Language Logo",
    show: () => true
  }, {
    src: require("./kotlin-1.svg"),
    alt: "Kotlin Language Logo",
    show: () => true
  }, {
    src: require("./kotlin-2.png"),
    alt: "Kotlin Language Logo",
    show: () => true
  }].filter(it => it.show());
}
