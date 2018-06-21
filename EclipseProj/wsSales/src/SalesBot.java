


import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;

import java.util.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


// �� ���� Ŭ����
@ServerEndpoint("/bothandle")
public class SalesBot extends Thread {
			
	// <�� �̸�, ����> �� => ���� �̺κ� �����Ͽ� ����, ����
	static Map<String, Boolean> map = new HashMap<String, Boolean>();
	
	// ���� �������� �ŷ� ����Ʈ
	static ArrayList<TradingElement> nowTrading = new ArrayList<TradingElement>();
	
	private String name;	// ���� �� �̸�
	private TradingElement tElement;
	
	public SalesBot() {}
	public SalesBot(String s, TradingElement t) { 
		this.name = s;
		this.tElement = t;
		}	// �� �̸� �����ϴ� ������
	
	// �� ���� �Լ�
	public void run() {
        System.out.println("client is now connected... thread");

    	initializing bot = new initializing(tElement); 
    	bot.main();
        
	}

	// ������ ���� json ���� ��
    @OnMessage
    public void handleMessage(String message){
        System.out.println("client is now connected... message");
        
        // json �Ľ�
        Gson gson = new Gson();
        TradingElement tInfo = gson.fromJson(message, TradingElement.class);
        
    	nowTrading.add(tInfo);	// ���� ���� ���� �ŷ� ���
    	
        // �� ���� ���� ���
        map.put(tInfo.getId()+tInfo.getName(), tInfo.getStatus());
        
        
        
        if(tInfo.getStatus()) {	// ���� ��ȣ ���� �� ���� ���� ����
            SalesBot bot = new SalesBot(tInfo.getId()+tInfo.getName(), tInfo);
            bot.start();
        }
        
        else {
        	for(int i = nowTrading.size() - 1; i >= 0; i--) {
        		
        		if((nowTrading.get(i).getId()+nowTrading.get(i).getName()).equals(tInfo.getId()+tInfo.getName())) {
        			nowTrading.remove(i);
        		}
    		} 
        }
    }
    
    @OnOpen
	public void handleOpen() {
        System.out.println("client is now connected... ");
	}

    @OnClose
    public void handleClose(){
        System.out.println("client is now disconnected...");
    }
    
    @OnError
    public void handleError(Throwable t){
        t.printStackTrace();
    }
}
