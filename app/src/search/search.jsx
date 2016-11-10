import React from 'react';
import ReactDOM from 'react-dom';
import styles from './search.less';

export default class Search extends React.Component {
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
    ReactDOM.findDOMNode(this.refs.search).focus();
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
