import React, {Component} from 'react';
import Home from './pages/Home';
import HomeList from './components/HomeList';
import HomeEdit from './components/HomeEdit';
import UpdateUserFrom from "./components/Users/Forms/UpdateUserFrom";

// import NavigationBar from "./components/NavigationBar";
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import {CookiesProvider} from 'react-cookie';

import './App.css';
import UserList from "./components/Users/UserList";

class App extends Component {
    render() {
        return (
            <CookiesProvider>
                <Router>
                    <Switch>
                        <Route path='/' exact={true} component={Home}/>
                        <Route path='/homes' exact={true} component={HomeList}/>
                        <Route path='/home/:id' exact={true} component={HomeEdit}/>
                        <Route path={'/users'} exact={true} component={UserList}/>
                        <Route path={'/user/:id'} exact={true} component={UpdateUserFrom}/>
                    </Switch>
                </Router>
            </CookiesProvider>
        )
    }
}

export default App;

