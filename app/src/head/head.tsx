import * as React from "react";

const styles = require("./head.less");

export class Head extends React.Component<{}, HeadState> {
  constructor() {
    super();
    this.state = {
      counter: 0
    };
  }

  changeLogo = () => {
    this.setState({
      counter: (this.state.counter + 1) % 3
    });
  };

  render() {
    return (
      <section className={styles.head}>
        <div className={styles.head_wrapper} onClick={this.changeLogo}>
          {getLogo(this.state.counter)}
        </div>
      </section>
    );
  }
}

interface HeadState {
  counter: number;
}

function getLogo(index: number) {
  switch (index) {
    case 0:
      return (
        <img src={require("./kotlin-0.svg")}
             alt="Kotlin Language Logo"
             className={styles.head_logo}/>
      );
    case 1:
      return (
        <img src={require("./kotlin-1.svg")}
             alt="Kotlin Language Logo"
             className={styles.head_logo}/>
      );
    case 2:
      return (
        <img src={require("./kotlin-2.png")}
             alt="Kotlin Language Logo"
             className={styles.head_logo}/>
      );
    default:
      return getLogo(0);
  }
}

