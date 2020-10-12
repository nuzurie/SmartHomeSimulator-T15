import React, {Component} from 'react';
import logo from './logo.svg';
import Home from './pages/Home';
import HomeList from './components/HomeList';
import HomeEdit from './components/HomeEdit';

import NavigationBar from "./components/NavigationBar";
import {BrowserRouter, BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import { CookiesProvider } from 'react-cookie';

import './App.css';

class App extends Component {
  render() {
    return (
        <CookiesProvider>
        <Router>
          <Switch>
            <Route path='/' exact={true} component={Home}/>
            <Route path='/homes' exact={true} component={HomeList}/>
              <Route path='/home/:id' component={HomeEdit}/>
          </Switch>
        </Router>
        </CookiesProvider>
    )
  }
}

export default App;

