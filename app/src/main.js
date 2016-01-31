import React from 'react';
import ReactDOM from 'react-dom';
import Logo from './logo.jsx';
import './style.less'
import 'css!normalize.css'

main();

function main() {
    ReactDOM.render(<Logo />, document.getElementById('root'));
}
