import java.sql.*;

public class DB {

	static void useDB(String Sql, String INSSEL) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null; //ResultSet ��ü ����
		
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        System.out.println("����̹� �ε� ����!");
	
	    } catch (ClassNotFoundException e) {
	       System.out.println(e.getMessage());
	    }
		
	    try {
	
	        String url = "jdbc:mysql://localhost:3306/test?autoReconnect=true&useSSL=false&characterEncoding=UTF-8&serverTimezone=UTC";
            System.out.println("�����ͺ��̽� ���� ����!");
	
	        con = DriverManager.getConnection(url,"root","");
	
	        System.out.println("�����ͺ��̽� ���� ����!");
	
	        stmt = con.createStatement();
	        
	        if (INSSEL == "insert") {
				stmt.executeUpdate(Sql);
	        	
	        } else if (INSSEL == "select"){
				rs = stmt.executeQuery(Sql);

//				while(rs.next()) {  
//					
//					API_KEY = rs.getString(1);
//					Secret_KEY = rs.getString(2);
//				}
	        	
	        } else { System.out.println("�����ͺ��̽� ��� ����!"); }
	
	
	        
//	        stmt.close();	
//	        con.close();	
	    } catch (SQLException se) {
            se.printStackTrace();
        } finally {		
            if(con!=null) try {con.close();} catch (SQLException e) {}
        }
	}
}
