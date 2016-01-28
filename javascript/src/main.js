import 'babel-polyfill';
import React from 'react';
import ReactDOM from 'react-dom';

import { App } from './components/app';

export function init() {
  ReactDOM.render(<App />, document.getElementById('app'));
}
