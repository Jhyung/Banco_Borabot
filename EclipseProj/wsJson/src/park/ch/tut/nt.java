package park.ch.tut;

import com.google.gson.*;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

import java.util.ArrayList;
import java.util.List;

class Member {
	
	private String name;
	private int age;
	private List<School> schools;
	
	public Member(String name, int age, List<School> schools){
		this.name = name;
		this.age = age;
		this.schools = schools;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public List<School> getSchools() {
		return schools;
	}

	public void setSchools(List<School> schools) {
		this.schools = schools;
	}
}



class School {
	
	private String name;
	private String location;
	private int enterYear;
	
	public School(String name, String location, int enterYear){
		this.name = name;
		this.location = location;
		this.enterYear = enterYear;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getEnterYear() {
		return enterYear;
	}

	public void setEnterYear(int enterYear) {
		this.enterYear = enterYear;
	}
	
}

@ServerEndpoint("/nowtrading")
public class nt {
	
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
        System.out.println("receive from client : "+message);
        String replymessage = "echo "+message;
        System.out.println("send to client : "+replymessage);
        //return replymessage;
        

		Gson gson = new Gson();
		
		List<School> schools = new ArrayList<School>();
		schools.add(new School("A�б�", "����", 2000));
		schools.add(new School("B�б�", "�뱸", 2003));
		schools.add(new School("C�б�", "����", 2006));
		
		String result = gson.toJson(new Member("lee", 20, schools));
		System.out.println("=========== JSON ǥ�������� ��ȯ ===========");
		System.out.println(result);
		
		Member mee = gson.fromJson(result, Member.class);
		
		System.out.println("");
		System.out.println("=========== �ڹ� ��ü�� ���� ===========");
		System.out.println(mee.getName());
		System.out.println(mee.getAge());
		for (School school : mee.getSchools()){
			System.out.println(school.getEnterYear()+", "
					+school.getName()+", "
					+school.getLocation());
		}
        return gson.toJson(result);
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
