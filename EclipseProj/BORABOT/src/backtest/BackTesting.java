package backtest;

import exchangeAPI.CryptowatchAPI;
import DB.DB;
import Indicator.*;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Stack;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


// 클래스 생성 
// run함수 실행 !

public class BackTesting {

	private static String exchange;
	private static String coin;
	private static String base;
	private static int interval;
	
	private static String email;
	private static String strategyName;
	private static String buyingSetting;
	private static String sellingSetting;
	private static String startDate; // 일단 가지고만 있기
	private static String endDate; // 루프 탈출 요소
	// 택 1
	private static double priceBuyUnit;
	private static double numBuyUnit;
	// 택 1
	private static double priceSellUnit;
	private static double numSellUnit;
	
	private static double buyCriteria;
	private static double sellCriteria;
	
	private double nowCash;
	private double nowCoin;
	private double initialCash = 100000000;
	private double initialCoin = 0;
	
	
	private String returnMessage = "";
	private String returnDetailMessage = "";
	private String returnResult = "";
	boolean flag = false;

	public String getReturnMsg() { return returnMessage+returnResult; }
	public String getReturnDetailMsg() { return returnDetailMessage; }
	public boolean getFlag() { return flag; }
	
	//크립토
	private static CryptowatchAPI crypt = new CryptowatchAPI(20, 60);
	
	public BackTesting(String email, String exchange, String coin, String base, int interval, String startDate, String endDate, String strategyName, String buyingSetting, String sellingSetting, double nowCash, double priceBuyUnit, double priceSellUnit, double numBuyUnit, double numSellUnit, int error) {
		
		// 앞단에서 받아오는 내용
		this.exchange = exchange;
		this.coin = coin;
		this.base = base;
		this.interval = interval;
		
		this.email = email; // 노필요
		this.strategyName = strategyName;
		this.startDate = startDate;
		this.endDate = endDate;
		
		this.buyingSetting = buyingSetting;
		this.sellingSetting = sellingSetting;
		this.priceBuyUnit = priceBuyUnit;
		this.numBuyUnit = numBuyUnit;
		this.priceSellUnit = priceSellUnit;
		this.numSellUnit = numSellUnit;
		
		this.nowCash = nowCash;
		this.initialCash = nowCash;
	}
	
	public void backTestRun() {

		//String returnMessage = "";
		//String returnDetailMessage = "";
		
		System.out.println("시작 코인 : " + nowCoin + " / 시작 금액 : " + (long)nowCash);
		
		CryptowatchAPI crypt = new CryptowatchAPI(10,10);
		//테스트를 위해 만든 JSON객체
		Gson gson = new Gson();
		
		JsonObject indicators = new JsonObject();
		
		JsonObject indicatorSetting = new JsonObject();
		indicatorSetting.addProperty("indicator", "BollingerBand");
		indicatorSetting.addProperty("weight", 1);
		indicatorSetting.addProperty("period", 12);
		indicatorSetting.addProperty("mul", 3);
		indicators.add("0", gson.toJsonTree(indicatorSetting));
		
		JsonObject indicatorSetting1 = new JsonObject();
		indicatorSetting1.addProperty("indicator", "CCI");
		indicatorSetting1.addProperty("weight", 1);
		indicatorSetting1.addProperty("period", "20");
		indicatorSetting1.addProperty("buyIndex", "70");
		indicatorSetting1.addProperty("sellIndex", "30");
		indicators.add("1", gson.toJsonTree(indicatorSetting1));
		
		JsonObject indicatorSetting2 = new JsonObject();
		indicatorSetting2.addProperty("indicator", "RSI");
		indicatorSetting2.addProperty("weight", 1);
		indicatorSetting2.addProperty("period", "20");
		indicatorSetting2.addProperty("buyIndex", "70");
		indicatorSetting2.addProperty("sellIndex", "30");
		indicators.add("2", gson.toJsonTree(indicatorSetting2));
		
		JsonObject indicatorSetting3 = new JsonObject();
		indicatorSetting3.addProperty("indicator", "StochOsc");
		indicatorSetting3.addProperty("weight", 1);
		indicatorSetting3.addProperty("period", "20");
		indicatorSetting3.addProperty("n", "15");
		indicatorSetting3.addProperty("m", "5");
		indicatorSetting3.addProperty("t", "3");
		indicators.add("3", gson.toJsonTree(indicatorSetting3));
		
		JsonObject indicatorSetting4 = new JsonObject();
		indicatorSetting4.addProperty("indicator", "CCI");
		indicatorSetting4.addProperty("weight", 1);
		indicatorSetting4.addProperty("period", "20");
		indicatorSetting4.addProperty("buyIndex", "70");
		indicatorSetting4.addProperty("sellIndex", "30");
		indicators.add("4", gson.toJsonTree(indicatorSetting4));
		
		JsonObject indicatorSetting5 = new JsonObject();
		indicatorSetting5.addProperty("indicator", "CCI");
		indicatorSetting5.addProperty("weight", 1);
		indicatorSetting5.addProperty("period", "20");
		indicatorSetting5.addProperty("buyIndex", "70");
		indicatorSetting5.addProperty("sellIndex", "30");
		indicators.add("5", gson.toJsonTree(indicatorSetting5));
		
		JsonObject indicatorSetting6 = new JsonObject();
		indicatorSetting6.addProperty("indicator", "CCI");
		indicatorSetting6.addProperty("weight", 1);
		indicatorSetting6.addProperty("period", "20");
		indicatorSetting6.addProperty("buyIndex", "70");
		indicatorSetting6.addProperty("sellIndex", "30");
		indicators.add("6", gson.toJsonTree(indicatorSetting6));
		
		JsonObject jsobj = new JsonObject();
		jsobj.add("indicatorList", gson.toJsonTree(indicators));
		jsobj.addProperty("buyCriteria", 1);
		jsobj.addProperty("sellCriteria", -1);
		jsobj.addProperty("expList", "and, or, and , or, or, or");
		//"and, or, and , or, or, or"
			
		String strategySettingJson1 = gson.toJson(jsobj);
		//System.out.println("json test : \n" + strategySettingJson);  -> 제이슨 테스트출력!!!
		
		// 디비에서 제이슨 불러오기 // 디비에서 제이슨 불러오기 // 디비에서 제이슨 불러오기 // 디비에서 제이슨 불러오기 // 디비에서 제이슨 불러오기 // 디비에서 제이슨 불러오기 //
		// String jsonSql = "SELECT strategy_content FROM custom_strategy WHERE email = '$email' and strategy_name = '$strategyName';"
		// Resultset rs = DB.query(jsonSql, "select");
		// String strategySettingJson = rs.get(1);
		String settingSelectSql = String.format("SELECT strategy_content FROM custom_strategy WHERE email = \"%s\" and strategy_name = \"%s\"; ", email, strategyName);
		String strategySettingJson="";
		
		DB useDB = new DB();
		try {
			ResultSet rsTemp = useDB.Query(settingSelectSql, "select");
			if(rsTemp.next()) {
				strategySettingJson = rsTemp.getString(1);
			}
			useDB.clean();
		} catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("test"+strategySettingJson);
		
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(strategySettingJson);
		JsonObject jsnObj = element.getAsJsonObject();
		
		buyCriteria = jsnObj.get("buyCriteria").getAsInt();
		sellCriteria = jsnObj.get("sellCriteria").getAsInt();
		String expList[] = jsnObj.get("expList").getAsString().split(",");
		
		JsonObject indicatorListJs = jsnObj.get("indicatorList").getAsJsonObject();
		calcIndicator_bt[] indicatorCalcer = new calcIndicator_bt[indicatorListJs.size()];
		int weightList[] = new int[indicatorListJs.size()];
		
		// System.out.println("jsonObj" + indicatorListJs);
		// maxPeriod 를 찾기
		// 각각의 period는 제이슨 활용
		
		// startDate -> UTC -> UTC - maxPeriod
		int maxPeriod = 0;
		//System.out.println("ListSize : " + indicatorListJs.size());
		
		for(int i = 0; i < indicatorListJs.size(); i++) {
			
			String indicator = indicatorListJs.get(i+"").getAsJsonObject().get("indicator").getAsString();
			int tempPeriod;
			
			if(indicatorListJs.get(i+"").getAsJsonObject().get("indicator").getAsString().equals("CCI")) {
				tempPeriod = indicatorListJs.get(i+"").getAsJsonObject().get("period").getAsInt() + indicatorListJs.get(i+"").getAsJsonObject().get("period").getAsInt() - 1;
			}
			else if(indicatorListJs.get(i+"").getAsJsonObject().get("indicator").getAsString().equals("BollingerBand")) {
				tempPeriod = indicatorListJs.get(i+"").getAsJsonObject().get("period").getAsInt();
			}
			else if(indicatorListJs.get(i+"").getAsJsonObject().get("indicator").getAsString().equals("gdCross")) {
				tempPeriod = indicatorListJs.get(i+"").getAsJsonObject().get("longD").getAsInt();
			}
			else if(indicatorListJs.get(i+"").getAsJsonObject().get("indicator").getAsString().equals("gdVCross")) {
				tempPeriod = indicatorListJs.get(i+"").getAsJsonObject().get("longD").getAsInt();
			}
			else if(indicatorListJs.get(i+"").getAsJsonObject().get("indicator").getAsString().equals("StochOsc")) {
				tempPeriod = indicatorListJs.get(i+"").getAsJsonObject().get("n").getAsInt() + indicatorListJs.get(i+"").getAsJsonObject().get("m").getAsInt() + indicatorListJs.get(i+"").getAsJsonObject().get("t").getAsInt() - 2;
			}
			else {
				tempPeriod = indicatorListJs.get(i+"").getAsJsonObject().get("period").getAsInt()+1;
			}
			
			if(maxPeriod < tempPeriod) {
				maxPeriod = tempPeriod;
				//System.out.println("max indicator : " + indicator);
			}
		}
		
		LocalDateTime startD = LocalDateTime.parse(startDate);
		LocalDateTime endD = LocalDateTime.parse(endDate);
		
		ZoneId zoneId = ZoneId.systemDefault();
		long startUnix = startD.atZone(zoneId).toEpochSecond() - (maxPeriod*interval);
		long endUnix = endD.atZone(zoneId).toEpochSecond();
		double[][] hHLCVArr;
		
		// 11시간을 더해줘야 한국시간이 됩니다~
		// 크립토와치 표준시가 뭔지 몰라요~
		try {
			hHLCVArr = IndicatorFunction_bt.get_HLCV_HistoryArray(crypt, exchange, coin, base, interval, startUnix, endUnix);
		} catch(Exception e) {
			System.out.println("초기에러 : 데이터 불러오기 실패");
			return;
		}
		
		/*
		for(int i = 0; i < hHLCVArr.length; i++) {
			for(int j = 0; j < hHLCVArr[i].length; j++) {
				System.out.print((long)hHLCVArr[i][j]+" ");
			}
			System.out.println();
		}
		*/
		
		// 지표 객체 생성 파트!!!! 
		
		// 배열에다가 해당하는 지표 객체 담기 (각각 개별 파라미터 및 웨이트 적용)
		for(int i = 0; i < indicatorListJs.size(); i++) {
			String indexOrder = i+"";
			String indicator = indicatorListJs.get(indexOrder).getAsJsonObject().get("indicator").getAsString();
			int weight = indicatorListJs.get(indexOrder).getAsJsonObject().get("weight").getAsInt();
			weightList[i] = weight;
			
			//System.out.println("bt" + i + "th "+indicator);
			
			try {
				if(indicator.equals("BollingerBand")) {
					int period = indicatorListJs.get(indexOrder).getAsJsonObject().get("period").getAsInt();
					int mul = indicatorListJs.get(indexOrder).getAsJsonObject().get("mul").getAsInt();
					
					indicatorCalcer[i] = new BollingerBand_bt(period, mul, crypt, exchange, coin, base, interval, hHLCVArr, maxPeriod-period , maxPeriod);
				}
				else if(indicator.equals("CCI")) {
					
					int period = indicatorListJs.get(indexOrder).getAsJsonObject().get("period").getAsInt();
					int buyIndex = indicatorListJs.get(indexOrder).getAsJsonObject().get("buyIndex").getAsInt();
					int sellIndex = indicatorListJs.get(indexOrder).getAsJsonObject().get("sellIndex").getAsInt();
					
					indicatorCalcer[i] = new CommodityChannelIndex_bt(period, buyIndex, sellIndex, crypt, exchange, coin, base, interval, hHLCVArr, maxPeriod-(period*2-1) ,maxPeriod);
				}
				else if(indicator.equals("gdCross")) {
					
					int longd = indicatorListJs.get(indexOrder).getAsJsonObject().get("longD").getAsInt();
					int shortd = indicatorListJs.get(indexOrder).getAsJsonObject().get("shortD").getAsInt();
					int mT = indicatorListJs.get(indexOrder).getAsJsonObject().get("mT").getAsInt();
					
					indicatorCalcer[i] = new gdCross_bt(longd, shortd, mT, crypt, exchange, coin, base, interval, hHLCVArr, maxPeriod-longd+1 ,maxPeriod);
				}
				else if(indicator.equals("gdVCross")) {
					
					int longd = indicatorListJs.get(indexOrder).getAsJsonObject().get("longD").getAsInt();
					int shortd = indicatorListJs.get(indexOrder).getAsJsonObject().get("shortD").getAsInt();
					int mT = indicatorListJs.get(indexOrder).getAsJsonObject().get("mT").getAsInt();
					
					indicatorCalcer[i] = new gdVCross_bt(longd, shortd, mT, crypt, exchange, coin, base, interval, hHLCVArr, maxPeriod-longd+1 ,maxPeriod);
				}
				else if(indicator.equals("MFI")) {
					
					int period = indicatorListJs.get(indexOrder).getAsJsonObject().get("period").getAsInt();
					int buyIndex = indicatorListJs.get(indexOrder).getAsJsonObject().get("buyIndex").getAsInt();
					int sellIndex = indicatorListJs.get(indexOrder).getAsJsonObject().get("sellIndex").getAsInt();
					
					indicatorCalcer[i] = new MFI_bt(period, buyIndex, sellIndex, crypt, exchange, coin, base, interval, hHLCVArr, maxPeriod-(period+1), maxPeriod);
				}
				else if(indicator.equals("StochOsc")) {
					
					int n = indicatorListJs.get(indexOrder).getAsJsonObject().get("n").getAsInt();
					int m = indicatorListJs.get(indexOrder).getAsJsonObject().get("m").getAsInt();
					int t = indicatorListJs.get(indexOrder).getAsJsonObject().get("t").getAsInt();
					
					indicatorCalcer[i] = new StochasticOsillator_bt(n, m, t, crypt, exchange, coin, base, interval, hHLCVArr, maxPeriod-(n+m+t-2) ,maxPeriod);
				}
				else if(indicator.equals("VolumeRatio")) {
					
					int period = indicatorListJs.get(indexOrder).getAsJsonObject().get("period").getAsInt();
					int buyIndex = indicatorListJs.get(indexOrder).getAsJsonObject().get("buyIndex").getAsInt();
					int sellIndex = indicatorListJs.get(indexOrder).getAsJsonObject().get("sellIndex").getAsInt();
					
					indicatorCalcer[i] = new VolumeRatio_bt(period, buyIndex, sellIndex, crypt, exchange, coin, base, interval, hHLCVArr, maxPeriod-(period+1) , maxPeriod);
				}
				else if(indicator.equals("RSI")) {
					
					int period = indicatorListJs.get(indexOrder).getAsJsonObject().get("period").getAsInt();
					int buyIndex = indicatorListJs.get(indexOrder).getAsJsonObject().get("buyIndex").getAsInt();
					int sellIndex = indicatorListJs.get(indexOrder).getAsJsonObject().get("sellIndex").getAsInt();
					
					indicatorCalcer[i] = new RSI_bt(period, buyIndex, sellIndex, crypt, exchange, coin, base, interval, hHLCVArr, maxPeriod-(period+1), maxPeriod);
				}
			} catch(Exception e) {
				///////////////////////////////////ERROR//////////////////////////////////////////
				// Sleep or Terminate?
				// 초기 생성 오류 -> 기다리는걸 추천... 
				System.out.println("error ! : " + LocalDate.now());
				e.printStackTrace();
				returnMessage += "초기 에러 : 재시작\n";
				i--;
				continue;
			}
		}
		
		//System.out.println("maxPeriod : " + maxPeriod);
		//System.out.println("whole size : " + hHLCVArr.length);
		
		// // // // // // // // // // // 테스팅 루프 시작!!
		// // // // // // // // // // // 테스팅 루프 시작!!
		int cnt = 0;
		for(int i = maxPeriod+1; i < hHLCVArr.length; i++) {
			
			//System.out.println((cnt++)+" th!");
			returnDetailMessage += (cnt++)+" th trade\n";
			double fin;
			Date date;
			try {
				fin = getFinalDetermin(toPostfix(indicatorCalcer, expList, weightList));
				date = new Date();
				date.setTime((long)hHLCVArr[i][4]*1000);
				//System.out.println("시간 : " + date);
				//System.out.println("가중치 계산 결과 : " + fin);
				returnDetailMessage += "시간 : " + date + "\n";
				returnDetailMessage += "가중치 계산 결과 : " + fin + "\n";
			}
			catch(Exception e) {
				//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡCW or ExchangeAPI err알람ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ//
				//종료 ? 컨티뉴?
				//System.out.println("error ! : " + LocalDate.now());
				//System.out.println("bt_FianlDetermin을 구하는 도중 일어난 에러! - cw나 exapi문제일 확률!");
				returnDetailMessage += "가중치 결정 값을 구하는 도중 일어난  error ! : " + LocalDate.now() +"\n";
				returnMessage += "가중치 결정 값을 구하는 도중 일어난 error ! : " + LocalDate.now() +"\n";
				e.printStackTrace();
				return;
			}
			
			double currentPrice = hHLCVArr[i][2];
			
			if(fin >= buyCriteria) {
				if(nowCash > 0) {
					if(buyingSetting.equals("buyAll")){
						nowCoin += nowCash/currentPrice;
						nowCash = 0;
					}
					else if(buyingSetting.equals("buyCertainPrice")) {
						
						if(nowCash > priceBuyUnit)
						{
							double coinToBuy = priceBuyUnit / currentPrice;
							nowCoin += coinToBuy;
							nowCash -= priceBuyUnit;
							//System.out.println("일정가 구매! 현재 금액 : " + nowCash + " / 현재 코인 : " + nowCoin + " / 총 재산 : " + (nowCoin*currentPrice+nowCash));
						}
						else {
							//System.out.println("일정가 구매 - fail : 잔액부족");
//							returnMessage +=  date+"\n"+"일정가 구매 - fail : 잔액부족"+"\n";
							returnDetailMessage += "일정가 구매 - fail : 잔액부족\n";
							continue;
						}
					}
					else {
						//buyCertainNumber 일정갯수구입
						if(nowCash > numBuyUnit * currentPrice) {
							nowCoin += numBuyUnit;
							nowCash -= currentPrice*numBuyUnit; 
							//System.out.println("일정수 구매! 현재 금액 : " + nowCash + " / 현재 코인 : " + nowCoin + " / 총 재산 : " + (nowCoin*currentPrice+nowCash));
						}
						else {
							//System.out.println("일정수 구매 - fail : 잔액부족");
//							returnMessage +=  date+"\n"+"일정수 구매 - fail : 잔액부족"+"\n";
							returnDetailMessage += "일정수 구매 - fail : 잔액부족\n";
							continue;
						}
					}
					
					String temp = String.format("구매! 현재 현금자산 보유량 : %s / 현재 코인 보유량 : %s / 현재 총 자산 : %s", (long)nowCash, (long)nowCoin, (long)(nowCoin*currentPrice+nowCash));
					//System.out.println(temp);
					returnMessage +=  date+"\n"+temp+"\n";
					returnDetailMessage += temp+"\n";
				}
				else {
					//System.out.println("구매 - no money!");
//					returnMessage += date+"\n구매 - no money!"+"\n";
					returnDetailMessage += "\n구매 - no money!"+"\n";
				}
//				returnMessage += "--------------------------------------------------------------\n";
			}
			
			
			else if(fin <= sellCriteria) {
				if(nowCoin > 0) {
					
					if(sellingSetting.equals("sellAll")) {

						nowCash += nowCoin*currentPrice;
						nowCoin = 0;
						//System.out.println("판매! 현재 금액 : " + nowCash + " / 현재 코인 : " + nowCoin + " / 총 재산 : " + (nowCoin*currentPrice+nowCash)); 
					}
					
					else if(sellingSetting.equals("sellCertainPrice")) {
						
						if(nowCoin >= priceSellUnit / currentPrice) {
							nowCash += priceSellUnit;
							nowCoin -= priceSellUnit/currentPrice;
							//System.out.println("일정가 판매! 현재 금액 : " + nowCash + " / 현재 코인 : " + nowCoin + " / 총 재산 : " + (nowCoin*currentPrice+nowCash)); 
						}
						else {
							//System.out.println("일정가 판매 - fail : 코인부족");
//							returnMessage += "일정가 판매 - fail : 코인부족"+"\n";
							returnDetailMessage += "일정가 판매 - fail : 코인부족"+"\n";
							continue;
						}
						
					}
					
					else {
						//일정갯수판매
						if(nowCoin >= numSellUnit) {
							nowCoin -= priceSellUnit;
							nowCash += priceSellUnit*currentPrice;
							//System.out.println("일정수 판매! 현재 금액 : " + nowCash + " / 현재 코인 : " + nowCoin + " / 총 재산 : " + (nowCoin*currentPrice+nowCash)); 
						}
						else {
							//System.out.println("일정수 판매 - fail : 코인부족");
//							returnMessage += "일정수 판매 - fail : 코인부족"+"\n";
							returnDetailMessage += "일정수 판매 - fail : 코인부족\n";
							continue;
						}
					}
					
					String temp = String.format("판매! 현재 현금자산 보유량 : %s / 현재 코인 보유량 : %s / 현재 총 자산 : %s", (long)nowCash, (long)nowCoin, (long)(nowCoin*currentPrice+nowCash));
					//System.out.println(temp);
					returnMessage +=  date+"\n"+temp+"\n";
					returnDetailMessage += temp+"\n";
					
				}
				else {
					//System.out.println("판매 - no coin!");
//					returnMessage+= date+"\n판매 - no coin!"+"\n";
					returnDetailMessage += "판매 - no coin!"+"\n";
				}
				
//				returnMessage += "--------------------------------------------------------------\n";
			}
			else {
				//대기
				//System.out.println("대기!"); 
				returnDetailMessage += "대기!\n";
			}
			//System.out.println("--------------------------------------------------------------");
			returnDetailMessage += "--------------------------------------------------------------\n";
		}
		
		System.out.println("\n		<<결과>>\n" + returnMessage);
		System.out.println("\n		<<상세결과>>\n" + returnDetailMessage);
		
		double finalAsset = (hHLCVArr[hHLCVArr.length-1][2] * nowCoin + nowCash);
		double profit = ( (finalAsset - initialCash) / initialCash) * 100.0;
		
		System.out.println(finalAsset + " " + initialCash);
		returnResult = "최종 코인 : " + nowCoin + " / 최종 금액 : " + nowCash + " / 최종 자산 : " + finalAsset + " / 수익률 : " + profit + "%";
		System.out.println(returnResult);
		
		//System.out.println("---결과---\n" + returnMessage);
	}
	
	private double getFinalDetermin(Stack<String> post) {
		
		// 현재 스택이 abc++식으로 되어있으므로
		// 뒤집어서 ++cba식으로 바꿔준다
		// pop을 하면 맨 뒤(a)부터 빠져나오기 때문!
		Stack<String> tempStk = new Stack<String>();
		
		int postSize = post.size();
		for(int i = 0; i < postSize; i++) {
			tempStk.push(post.pop());
		}
		
		// 계산스택
		// 연산자를 만나면 두개를 팝해서 연산한 뒤 다시 푸쉬
		Stack<String> calStk = new Stack<String>();
		int size = tempStk.size();
		for(int i = 0; i < size; i++){
			
			String poped = tempStk.pop();
			
			if(poped.equals("or")) {
				
				String temp1 = calStk.pop();
				String temp2 = calStk.pop();
				
				int ret = Integer.parseInt(temp1) + Integer.parseInt(temp2);
				calStk.push(ret+"");
			}
			else if(poped.equals("and")) {
				String temp1 = calStk.pop();
				String temp2 = calStk.pop();
				
				int ret = Integer.parseInt(temp1) * Integer.parseInt(temp2);
				calStk.push(ret+"");
			}
			else {
				calStk.push(poped);
			}
		}
		//printStack(calStk);
		return Double.parseDouble(calStk.pop());
	}
	
	private Stack<String> toPostfix (calcIndicator_bt[] indicatorCalcer, String[] expList, int[] weightList) throws Exception{
		
		// 피연산자 리스트
		// 연산자 리스트
		// 가 있다. 그러면 연산자 리스트는 피연산자 리스트보다 항상 1개가 작을 수 밖에 없다.
		// 피연산자는 후위표기스택에 넣고, 연산자는 따로 수식스택에 넣고 계산한다.
		
		Stack<String> postStk = new Stack<String>();
		Stack<String> expStk = new Stack<String>();
		
		for(int i = 0; i < expList.length; i++) {
			try {
				
				String tempStr = indicatorCalcer[i].getDeterminConstant()*weightList[i]+"";
				//System.out.println(indicatorCalcer[i].toString().split("@")[0] + " : " + tempStr);
				returnDetailMessage += indicatorCalcer[i].toString().split("@")[0] + " : " + tempStr + "\n";
				postStk.push(tempStr);
				
			} catch(Exception cE) {
				cE.printStackTrace();
				System.out.println();
				System.out.println("toPostFix error");
				throw new Exception();
			}
			if(expStk.isEmpty()) {
				expStk.push(expList[i]);
			}
			else {
				if(expList[i].equals("and")) {

					if(expStk.lastElement().equals("and")) {
						postStk.push(expStk.pop());
					}
					expStk.push(expList[i]);

				}
				else if(expList[i].equals("or")) {
					
					while(!expStk.empty()) {
						postStk.push(expStk.pop());
					}
					expStk.push(expList[i]);
				}
			}
		}
		String tempStr = indicatorCalcer[indicatorCalcer.length-1].getDeterminConstant()*weightList[weightList.length-1]+"";
		//System.out.println(indicatorCalcer[indicatorCalcer.length-1].toString().split("@")[0] + " : " + tempStr);
		returnDetailMessage += indicatorCalcer[indicatorCalcer.length-1].toString().split("@")[0] + " : " + tempStr + "\n";
		
		postStk.push(tempStr);
		while(!expStk.isEmpty()) {
			postStk.push(expStk.pop());
		}
		//printStack(postStk);
		return postStk;
	}
}
