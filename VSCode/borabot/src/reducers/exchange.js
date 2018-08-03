// =============================================
// 거래소 정보 저장 state (거래 생성, 백테스팅에 사용)
// =============================================

const BITHUMB = {
  name: 'BITHUMB',
  baseList: [ "KRW"],
  coin: [
    {
    base: "KRW",
    list: [ "BTC", "ETH", "DASH", "LTC", "ETC", "XRP", "BCH", "XMR", "ZEC", "BTG", "EOS" ]
    }
  ]
}

const COINONE = {
  name: 'COINONE',
  baseList: [ "KRW"],
  coin: [
    {
    base: "KRW",
    list: [ "BTC", "BCH", "ETH", "ETC", "LTC", "BTG" ]
    }
  ]
}

const BINANCE = {
  name: 'BINANCE',
  baseList: [ "BTC", "USDT"],
  coin: [
    {
      base: "BTC",
      list: [ "ETH", "TRX", "XRP", "NEO", "BCD", "BNB", "VIB", "WTC", "ELF", "ICX", "IOST","VEN", "EOS", "XLM", "XVG", "PPT", "ONT", "OMG" ]
    },
    {
      base: "USDT",
      list: [ "BCC", "BNB", "BTC", "ETH", "LTC", "NEO", "ADA", "EOS", "IOTA", "TUSD", "XLM", "XRP", "ICX", "ONT" ]
    }
  ]
}

const Exchange = {
	exchangeList: [ 
    { key: "BITHUMB", value: BITHUMB },
    { key: "COINONE", value: COINONE },
    { key: "BINANCE", value: BINANCE }
  ],
  intervalList: [
    { key: "5분", value: "300" },
    { key: "30분", value: "1800" },
    { key: "1시간", value: "3600" },
    { key: "6시간", value: "21600" },
    { key: "12시간", value: "43200" },
    { key: "24시간", value: "86400" }
  ]
};

export const exchange = (state = Exchange, action) => {
	switch(action.type) {
		default:
			return state;
	}
};