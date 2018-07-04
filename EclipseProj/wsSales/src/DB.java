import java.sql.*;

public class DB {
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null; //ResultSet ��ü ����

	public ResultSet Query(String Sql, String INSSEL) {
		
		// ����̹� �ε�
	    try {	
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        System.out.println("����̹� �ε� ����!");
	
	    } catch (ClassNotFoundException e) {
	       System.out.println(e.getMessage());
	    }
		
	    try {	
	    	// DB ����
	    	String url = "jdbc:mysql://localhost:3306/test?autoReconnect=true&useSSL=false&characterEncoding=UTF-8&serverTimezone=UTC";
	        con = DriverManager.getConnection(url,"root","1111");	
	        System.out.println("�����ͺ��̽� ���� ����!");
	
	        stmt = con.createStatement();
	        
	        // INSERT��
	        if (INSSEL == "insert") {
				stmt.executeUpdate(Sql);
	        }
	        // SELECT��
	        else if (INSSEL == "select"){
				rs = stmt.executeQuery(Sql);	        	
	        } else { System.out.println("�����ͺ��̽� ���� ����!"); }

			if(rs !=null) rs.close();			
			if(stmt != null) stmt.close();			
			if(con!= null) con.close();
	        	        
	    } catch (SQLException se) {
            se.printStackTrace();
        } finally {		
            if(con!=null) try {con.close();} catch (SQLException e) {}
        }
        return rs;	
	}
}
