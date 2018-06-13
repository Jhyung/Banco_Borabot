package ws.sales.first;


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
	
	public SalesBot() {}
	public SalesBot(String s) { this.name = s; }	// �� �̸� �����ϴ� ������
	
	// �� ���� �Լ�
	public void run() {
        System.out.println("client is now connected... thread");
        
        try {
            //���� ��ü ����
            File file = new File("/usr/local/server/apache-tomcat-8.0.52/webapps/"+this.name+".txt");
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            
            /*
             * ����ٰ� ���� ȣ���ϸ� �ɵ�
             */
            
    		while(map.get(this.name)) {
                if(file.isFile() && file.canWrite()){ 
	                Date d = new Date();            
	                String time = d.toString();
                    bufferedWriter.write(this.name + " " + time + map + "\n" + nowTrading);	// ����
                    bufferedWriter.newLine();	// ���๮�ھ���  
                }
    			Thread.sleep(2000);	// �̺κ��̳� ���� �Լ����� �����ؼ� �ŷ��ϸ� �ɵ�
    		}      
            bufferedWriter.close();	// �α״� �ӽ÷� �������� �ѹ��� �����ϰ� �س�
            
        } catch(Exception e) {        	
        }
	}

	// ������ ���� json ���� ��
    @OnMessage
    public void handleMessage(String message){
        System.out.println("client is now connected... message");
        
        // json �Ľ�
        Gson gson = new Gson();
        TradingElement tInfo = gson.fromJson(message, TradingElement.class);
        
    	nowTrading.add(tInfo);
    	
        // �� ���� ���� ���
        map.put(tInfo.getId()+tInfo.getName(), tInfo.getStatus());
        
        
        if(tInfo.getStatus()) {	// ���� ��ȣ ���� �� ���� ���� ����
            SalesBot bot = new SalesBot(tInfo.getId()+tInfo.getName());
            bot.start();
        }
        
        else {
        	for(int i = nowTrading.size() - 1; i >= 0; i--) {
        		
        		if((nowTrading.get(i).getId()+nowTrading.get(i).getName()).equals(tInfo.getId()+tInfo.getName())) {
        			System.out.println("�����???");
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
