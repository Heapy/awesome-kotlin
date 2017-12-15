import * as React from "react";
import * as ReactDOM from "react-dom";
import {withRouter} from "react-router";
import * as qs from "query-string";

const styles = require("./search.less");

class SearchComponent extends React.Component<SearchProps, SearchState> {
  constructor(props) {
    super(props);
    this.state = {value: ""};
  }

  handleChange = (event) => {
    this.setState({value: event.target.value});
    this.props.onChange(event.target.value);
  };

  handleKeyPress = (event) => {
    if (event.key === "Enter") {
      event.preventDefault();
      return false;
    }
  };

  componentDidMount() {
    const query = qs.parse(this.props.location.search).q;
    if (query) {
      this.setState({value: query});
      this.props.onChange(query);
    }
    (ReactDOM.findDOMNode(this.refs["search"]) as HTMLInputElement).focus();
  };

  render() {
    return <section className={styles.search}>
      <form className={styles.search_wrapper}>
        <input className={styles.search_field}
               ref="search"
               onKeyPress={this.handleKeyPress}
               onChange={this.handleChange}
               placeholder="Type to Filter"
               value={this.state.value}/>
      </form>
    </section>;
  }
}

interface SearchProps {
  onChange: (value: any) => void;
  location?: any;
}

interface SearchState {
  value: string;
}

export const Search = withRouter(SearchComponent);
