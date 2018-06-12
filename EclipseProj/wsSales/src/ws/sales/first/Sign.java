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

class SignInfo {
	private String eMail;	// �̸���
	private String phone;	// ��ȭ��ȣ
	private String id;	// ID
	private String password;	// ��й�ȣ

	public String getEMail() { return eMail; }
	public String getPhone() { return phone; }
	public String getId() { return id; }
	public String getPassword() { return password; }
}

@ServerEndpoint("/signhandle")
public class Sign {
	// ������ ���� json ���� ��
    @OnMessage
    public Boolean handleMessage(String message){
        System.out.println("client is now connected... message");
        
        // json �Ľ�
        Gson gson = new Gson();
        SignInfo signInfo = gson.fromJson(message, SignInfo.class);
        
        // DB�� ���� �ְ�
        {
            if (signInfo.getId()== "db�� �ִ� ��� ������") {	// if�� ���� db �⺻Ű �ߺ� �����̸� ���� ����
	            return false;	// ���� ����
            }
            else {	// ���� ������ ���� �޼��� ����
            	return true;	// ���� ����
            }        	
        }        
    }
    
    @OnOpen
	public void main() {
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
