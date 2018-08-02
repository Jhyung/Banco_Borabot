import React, { Component } from 'react';
import axios from 'axios';
import { connect } from 'react-redux';

import DayPickerInput from 'react-day-picker/DayPickerInput';
import 'react-day-picker/lib/style.css';

import './Sales.css';

const hourList = []

for(var i=1;i<=24;i++) hourList.push(i-1)  

class Sales extends Component {
  constructor(props) {
    super(props);

    this.state = {
      exchangeIndex: 0,
      baseIndex: 0,
      selectedDay: new Date()
    };
  }

  handleDayChange = (day) => {
    this.setState({ selectedDay: day });
  }

  handleIndex = () => {
    this.setState({
      exchangeIndex: document.getElementById('exchange').selectedIndex,
      baseIndex: document.getElementById('base').selectedIndex
    })
  }

  handleStartbtn = () => {

    if(document.getElementById('botname').value === ''){
      alert('😆 당신의 지갑을 풍족하게 해줄 귀여운 봇의 이름을 지어주세요 😆')
      return
    }

    const { selectedDay } = this.state
    var endDate = selectedDay.getFullYear()+'-'+
      ("0"+(selectedDay.getMonth()+1)).slice(-2)+'-'+
      ("0"+selectedDay.getDate()).slice(-2)+'T'+
      ("0"+document.getElementById('endHour').value).slice(-2)+':00:00.000'

    var now = new Date();
    if(new Date(endDate) - now < 0){
      alert('😆 거래 종료를 과거에 할 순 없어요 😆')
      return
    }
    
    var startDate = now.getFullYear()+'-'+
      ("0"+(now.getMonth()+1)).slice(-2)+'-'+
      ("0"+now.getDate()).slice(-2)+'T'+
      ("0"+now.getHours()).slice(-2)+':'+
      ("0"+now.getMinutes()).slice(-2)+':'+
      ("0"+now.getSeconds()).slice(-2)+'.000'

    let alertMsg = document.getElementById('botname').value + '\n' + 
      document.getElementById('exchange').value + '\n' +
      document.getElementById('coin').value + '\n' +
      document.getElementById('base').value + '\n' +
      document.getElementById('interval').value+ '\n' + 
      document.getElementById('strategy').value+ '\n' +
      document.getElementById('buyingSetting').value+ '\n' +
      document.getElementById('sellingSetting').value+ '\n' +
      endDate+ '\n' +
      '\n이 맞습니까?';

    if(window.confirm(alertMsg)){
      axios.post( 
        'TradeMain', 
        'status='+true+
        '&botname='+document.getElementById('botname').value+
        '&exchange='+document.getElementById('exchange').value+
        '&coin='+document.getElementById('coin').value+
        '&base='+document.getElementById('base').value+ 
        '&interval='+this.props.intervalList[document.getElementById('interval').selectedIndex].value+
        '&strategyName='+document.getElementById('strategy').value+
        '&buyingSetting='+document.getElementById('buyingSetting').value+
        '&sellingSetting='+document.getElementById('sellingSetting').value+
        '&startDate='+startDate+
        '&endDate='+endDate,
        { 'Content-Type': 'application/x-www-form-urlencoded' }
      )
      alert('거래가 시작되었습니다.')
    } else alert('취소되었습니다.')
  }

  render() {
    const { exchangeList, intervalList, strategyList } = this.props
    const { exchangeIndex, baseIndex } = this.state

    return (
      <div>        
        <h4 className="Sales-color">Sales configuration</h4>
        <input placeholder="이름" id="botname"/><br/>
        거래소 : <select id="exchange" onChange={this.handleIndex}>
          {exchangeList.map((exchange, index) => {
            return (<option key={index} > {exchange.key} </option>)
          })
          }
        </select><br/>
        기축통화 : <select id="base" onChange={this.handleIndex}>
          {exchangeList[exchangeIndex].value.baseList.map((base, i) => {
            return (<option key={i}> {base} </option>)
          })}
        </select><br/>
        코인 : <select id="coin">
          {exchangeList[exchangeIndex].value.coin[baseIndex].list.map((coin, i) => {
            return (<option key={i}> {coin} </option>)
          })}
        </select><br/>
        거래 간격 : <select id="interval">
          {intervalList.map((int, i) => {
            return (<option key={i}> {int.key} </option>)
          })}
        </select><br/>
        전략 : <select id="strategy">
          {strategyList.map((s, i) => {
            return (<option key={i}> {s.name} </option>)
          })}
        </select><br/>        
        구매 설정 : <select id="buyingSetting">
            <option> buyAll </option>
        </select><br/>
        판매 설정 : <select id="sellingSetting">
            <option> sellAll </option>
        </select><br/>
        종료일 : 
        <DayPickerInput onDayChange={this.handleDayChange} />
        <select id="endHour">
          {hourList.map((e, i) => {
            return (<option key={i} selected={e === new Date().getHours()}> {e} </option>)
          })}
        </select>시<br/>
        <button onClick={this.handleStartbtn}>거래 시작</button>
      </div>
    );
  }

}

let mapStateToProps = (state) => {
  return {
    strategyList: state.strategy.strategyList,
    exchangeList: state.exchange.exchangeList,
    intervalList: state.exchange.intervalList
  };
}

Sales = connect(mapStateToProps)(Sales);

export default Sales;