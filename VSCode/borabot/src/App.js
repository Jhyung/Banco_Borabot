import React, { Component } from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import axios from 'axios';
import { connect } from 'react-redux';

import Header from './header/Header';
import Main from './main/Main';
import Profile from './profile/Profile';
import Board from './board/Board';
import BackTesting from './backTesting/BackTesting';
import Strategy from './strategy/Strategy';
import Log from './log/Log'

import Login from './initial/Login';
import Register from './initial/Register';
import FindInfo from './initial/FindInfo';

import { login, logout } from './reducers/logInOut';
import { setStrategy } from './reducers/strategy';

class App extends Component {
  componentDidMount() {    
    // 세션의 현재 로그인 여부 확인
    axios.get('Status')
    .then( response => {
      if(response.data.status) this.props.onLogin()
      else this.props.onLogout()
    }) 
    .catch( response => { console.log('err\n'+response); } ); // ERROR    
    
    axios.get( 'Strategy' )
    .then( response => {
      this.props.onSetStrategy(response.data)
    }) 
    .catch( response => { console.log('err\n'+response); } ); // ERROR
  }

  render() {
    return (
      <BrowserRouter basename={process.env.REACT_APP_ROUTER_BASE || ''}>
        {this.props.login
        ? <div>
            <Header/>
            <Switch>
              <Route path="/profile" component={Profile}/>
              <Route path="/backtesting" component={BackTesting}/>
              <Route path="/board" component={Board}/>
              <Route path="/log" component={Log}/>
              <Route path="/strategy" component={Strategy}/>
              <Route path="/" component={Main}/>
            </Switch>
          </div>
        : <Switch>
            <Route path="/register" component={Register}/>
            <Route path="/findInfo" component={FindInfo}/>
            <Route path="/" component={Login}/>
          </Switch>
        }
      </BrowserRouter>
    );
  }
}

let mapDispatchToProps = (dispatch) => {
  return {
    onLogin: () => dispatch(login()),
    onLogout: () => dispatch(logout()),
    onSetStrategy: (value) => dispatch(setStrategy(value))
  }
}

let mapStateToProps = (state) => {
  return {
    login: state.logInOut.login,
    strategyList: state.strategy.strategyList
  };
}

App = connect(mapStateToProps, mapDispatchToProps)(App);

export default App;
