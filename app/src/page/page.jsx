import React from 'react';
import Head from '../head/head.jsx';
import Search from '../search/search.jsx';

export default class Page extends React.Component {
  render() {
    return (
      <div>
        <Head />
        <Search />
      </div>
    );
  }
}
