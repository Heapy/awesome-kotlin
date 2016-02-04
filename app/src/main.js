import React from 'react';
import ReactDOM from 'react-dom';
import Page from './page/page.jsx';
import Data from '../Kotlin';
import './style.less'
import 'css!normalize.css'

function main() {
    ReactDOM.render(<Page categories={Data}/>, document.getElementById('root'));
}

main();
