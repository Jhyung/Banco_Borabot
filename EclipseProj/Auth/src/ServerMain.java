


import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;

import java.util.*;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;


// �� ���� Ŭ����
@ServerEndpoint("/mainhandle")
public class ServerMain extends Thread {
			
	// <�� �̸�, ����> �� => ���� �̺κ� �����Ͽ� ����, ����
	static Map<String, Boolean> map = new HashMap<String, Boolean>();
	
	// ���� �������� �ŷ� ����Ʈ
	static ArrayList<TradingElement> nowTrading = new ArrayList<TradingElement>();
	
	private String name;	// ���� �� �̸�
	private TradingElement tElement;
	
	public ServerMain() {}
	public ServerMain(String s, TradingElement t) { 
		this.name = s;
		this.tElement = t;
		}	// �� �̸� �����ϴ� ������
	
	// �� ���� �Լ�
	public void run() {
        System.out.println("������ ����");

    	initializing bot = new initializing(tElement); 
    	bot.main();
        
	}

	// ������ ���� json ���� ��
    @OnMessage
    public String handleMessage(String message){
        System.out.println("�޽����� �޾ҽ��ϴ�");
        
//        DB test = new DB();
//        test.UsingDB();
        
        // json �Ľ�
        Gson gson = new Gson();
        TradingElement tInfo = gson.fromJson(message, TradingElement.class);

    	String ntJson = gson.toJson(tInfo);
    	tInfo.setResDate();
    	tInfo.profit = "110.11";
    	
    	return ntJson;
//    	nowTrading.add(tInfo);	// ���� ���� ���� �ŷ� ���
//    	
//        // �� ���� ���� ���
//        map.put(tInfo.getId()+tInfo.getName(), tInfo.getStatus());
//        
//        
//        
//        if(tInfo.getStatus()) {	// ���� ��ȣ ���� �� ���� ���� ����
//            ServerMain bot = new ServerMain(tInfo.getId()+tInfo.getName(), tInfo);
//            bot.start();
//        }
//        
//        else {
//        	for(int i = nowTrading.size() - 1; i >= 0; i--) {
//        		
//        		if((nowTrading.get(i).getId()+nowTrading.get(i).getName()).equals(tInfo.getId()+tInfo.getName())) {
//        			nowTrading.remove(i);
//        		}
//    		} 
//        }
    }
    
    @OnOpen
	public void handleOpen() {
        System.out.println("Hello ServerMain!");
	}

    @OnClose
    public void handleClose(){
        System.out.println("ByeBye ServerMain~");
    }
    
    @OnError
    public void handleError(Throwable t){
        t.printStackTrace();
    }
}
