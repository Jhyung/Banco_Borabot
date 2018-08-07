import React, { Component } from 'react';
import axios from 'axios';
import { connect } from 'react-redux';

import { selectTrading } from './reducers/sales';

import './NowTrading.css';

class NowTrading extends Component {
  constructor(props) {
    super(props);

    this.state = {
      listE: []
    };
  }
  componentDidMount() {    
    axios.get('NowTrading')
    .then( response => {
      this.setState({
        listE: response.data
      })
    }) 
    .catch( response => { console.log('err\n'+response); } ); // ERROR
  }

  handleStopbtn = (nt) => {
    alert(nt.bot_name + " 거래를 중지하시겠습니까?");

    axios.post( 
      'TradeMain', 
      'status='+false+
      '&botname='+nt.bot_name,
      { 'Content-Type': 'application/x-www-form-urlencoded' }
    )
  }

  handleLogbtn = (nt) => {
    this.props.onSelectTrading(nt)
  }

  reload = () => {
    axios.get('NowTrading')
    .then( response => {
      this.setState({
        listE: response.data
      })
    }) 
    .catch( response => { console.log('err\n'+response); } ); // ERROR
    this.forceUpdate(); // 새로고침
  }

  render() {
    return(
      <div >
      <button onClick={this.reload}>새로고침</button>
      <div className = "NowTrading-elementList">
      {this.state.listE.map((nt, i) => {
        return (
        <div className = "NowTrading-element" >
          <b>{nt.bot_name}</b><br/>코인 : {nt.coin}<br/>거래소 : {nt.exchange_name}<br/>
          전략 : {nt.strategy_name}<br/>종료일 : {nt.end_date}<br/>수익률 : {nt.profit}%<br/>
          <button id="Sale-log-btn" onClick={() => this.handleLogbtn(nt)}>거래 기록</button>
          <button id="Sale-stop-btn" onClick={() => this.handleStopbtn(nt)}>거래 종료</button>
        </div>);
      })}
      </div></div>
    );
  }
}

let mapDispatchToProps = (dispatch) => {
  return {
    onSelectTrading: (value) => dispatch(selectTrading(value))
  }
}

NowTrading = connect(undefined, mapDispatchToProps)(NowTrading);

export default NowTrading;