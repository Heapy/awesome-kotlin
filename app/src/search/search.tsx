import * as React from 'react';
import * as ReactDOM from 'react-dom';
const styles = require('./search.less');

export class Search extends React.Component<{onChange: (a:any) => void}, {value: any}> {
  constructor(props) {
    super(props);
    this.state = {value: ''};
  }

  handleChange(event) {
    this.setState({value: event.target.value});
    this.props.onChange(event.target.value);
  }

  handleKeyPress(event) {
    if (event.key === 'Enter') {
      event.preventDefault();
      return false;
    }
  }

  componentDidMount() {
    (ReactDOM.findDOMNode(this.refs['search']) as HTMLInputElement).focus();
  }

  render() {
    return <section className={styles.search}>
      <form className={styles.search_wrapper}>
        <input className={styles.search_field}
               ref="search"
               onKeyPress={this.handleKeyPress}
               onChange={this.handleChange.bind(this)}
               placeholder="Type to Filter"
               value={this.state.value}/>
      </form>
    </section>;
  }
}
