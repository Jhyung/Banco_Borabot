package com.za.tutorial.websocket;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/serverendpointdemo")
public class ServerEndpotintDemo {
	
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
    public String handleMessage(String message){
    	/*
    	ArrayList<Integer> a = new ArrayList<>();
    	ArrayList<Integer> b = new ArrayList<>();
    	a.add(1);	a.add(2);	a.add(3);
    	b.add(4);	b.add(5);	b.add(6);
    	ArrayList<ArrayList<Integer>> d = new ArrayList<>();
    	d.add(a);
    	d.add(b);
    	
        JSONObject obj = new JSONObject();
		try {
			JSONArray jArray = new JSONArray();//�迭�� �ʿ��Ҷ�
			for (int i = 0; i < d.size(); i++)//�迭
			{
				JSONObject sObject = new JSONObject();//�迭 ���� �� json
				sObject.put("contentid", d.get(i).get(0));
				sObject.put("contentid", d.get(i).get(1));
				sObject.put("contentid", d.get(i).get(2));
				jArray.put(sObject);
			}
			obj.put("planName", "planA");
			obj.put("id", "userID");
			obj.put("item", jArray);//�迭�� ����
			
			System.out.println(obj.toString());
		
		} catch (JSONException e) {
			e.printStackTrace();	
		}*/
        System.out.println("receive from client : "+message);
        String replymessage = "echo "+message;
        System.out.println("send to client : "+replymessage);
        //return obj.toString();
        return replymessage;
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