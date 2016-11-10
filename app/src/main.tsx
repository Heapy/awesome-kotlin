import * as React from 'react';
import * as ReactDOM from 'react-dom';
import {Page} from './page/page';
import './style.less'
import 'css!normalize.css'

const Data = require('../LinksWithStars.json');

ReactDOM.render(
    <Page categories={Data}/>,
    document.getElementById('root')
);

