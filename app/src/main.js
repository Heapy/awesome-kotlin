import React from 'react';
import ReactDOM from 'react-dom';
import Page from './page/page.jsx';
import './style.less'
import 'css!normalize.css'

main();

function main() {
    ReactDOM.render(<Page />, document.getElementById('root'));
}
