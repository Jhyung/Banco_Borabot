
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

//���� ���� �ŷ� ���� ����
@ServerEndpoint("/authhandle")
public class User {

    @OnMessage
    public void handleMessage(String auth){
        System.out.println("���� ����!");
    }
    
    @OnOpen
	public void hadleOpen() {
        System.out.println("ȸ������ ����!");
	}

    @OnClose
    public void handleClose(){
        System.out.println("ȸ������ ����!");
    }
    
    @OnError
    public void handleError(Throwable t){
        t.printStackTrace();
    }
}
