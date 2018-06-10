package com.za.tutorial.websocket;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

import java.io.File;
import java.io.FileWriter;

@ServerEndpoint("/serverendpointdemo")
public class ServerEndpointDemo {    /***
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
        System.out.println("receive from client : "+message);
        String replymessage = "echo "+message;
        System.out.println("send to client : "+replymessage);
        
        
       String txt = "�׽�Ʈ�Դϴ�!!" ;
        
       String fileName = "/usr/local/server/test11.txt" ;
        
        
       try{
            
           // ���� ��ü ����
           File file = new File(fileName) ;
            
           // true ������ ������ ���� ���뿡 �̾ �ۼ�
           FileWriter fw = new FileWriter(file, true) ;
            
           // ���Ͼȿ� ���ڿ� ����
           fw.write(txt);
           fw.flush();

           // ��ü �ݱ�
           fw.close();
            
            
       }catch(Exception e){
           e.printStackTrace();
       }
	
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