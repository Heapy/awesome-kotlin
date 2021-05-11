import * as React from "react";
import {withRouter} from "react-router";
import "./search.less";

class SearchComponent extends React.Component<SearchProps, SearchState> {
  private readonly inputRef: React.RefObject<HTMLInputElement>;
  constructor(props) {
    super(props);
    this.state = {value: ""};
    this.inputRef = React.createRef();
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
    const query = new URLSearchParams(this.props.location.search).get("q");
    if (query) {
      this.setState({value: query});
      this.props.onChange(query);
    }
    this.inputRef.current.focus();
  };

  render() {
    return <section className="search">
      <form className="search_wrapper">
        <input className="search_field"
               ref={this.inputRef}
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
