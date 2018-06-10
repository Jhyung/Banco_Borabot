package ws.sales.first;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

import java.io.BufferedWriter;
import java.io.FileWriter;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@ServerEndpoint("/jsales")
public class JSales {	
	/***
     * �� ������ ����Ǹ� ȣ��Ǵ� �̺�Ʈ
     */
    @OnOpen
    public void handleOpen(){
        System.out.println("client is now connected...");
    }
    /**
     * �� �������κ��� �޽����� ���� ȣ��Ǵ� �̺�Ʈ
     * @param message
     * @return
     */
    @OnMessage
    public void handleMessage(String message){
        System.out.println("receive from client : "+message);
        try {
            JSONParser jsonParser = new JSONParser();
            
            //JSON�����͸� �־� JSON Object �� ����� �ش�.
            JSONObject jsonObject = (JSONObject) jsonParser.parse(message);
            
            //books�� �迭�� ����
            JSONArray salesInfoArray = (JSONArray) jsonObject.get("sales");

            System.out.println("=====Members=====");
            for(int i=0 ; i<salesInfoArray.size() ; i++){
                JSONObject tempObj = (JSONObject) salesInfoArray.get(i);
                System.out.println(""+(i+1)+"��° ����� �̸� : "+tempObj.get("��������"));
                System.out.println(""+(i+1)+"��° ����� �̸��� : "+tempObj.get("�ŷ���"));
                System.out.println(""+(i+1)+"��° ����� ���� : "+tempObj.get("����"));
                System.out.println(""+(i+1)+"��° ����� ���� : "+tempObj.get("�ݾ�"));
                System.out.println(""+(i+1)+"��° ����� ���� : "+tempObj.get("�Ⱓ"));
                System.out.println("----------------------------");
            }
                   	
        }
        catch (ParseException e) {
        	e.printStackTrace();
        }
        String fileName = "/usr/local/server/res.txt" ;
        
        
        try{
                         
            // BufferedWriter �� FileWriter�� �����Ͽ� ��� (�ӵ� ���)
            BufferedWriter fw = new BufferedWriter(new FileWriter(fileName, true));
             
            // ���Ͼȿ� ���ڿ� ����
            fw.write(message);
            fw.flush();
 
            // ��ü �ݱ�
            fw.close();
             
             
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    /**
     * �� ������ ������ ȣ��Ǵ� �̺�Ʈ
     */
    @OnClose
    public void handleClose(){
        System.out.println("client is now disconnected...");
    }
    /**
     * �� ������ ������ ���� ȣ��Ǵ� �̺�Ʈ
     * @param t
     */
    @OnError
    public void handleError(Throwable t){
        t.printStackTrace();
    }
}
