

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

/**
 * Servlet implementation class SendNowTrading
 */
@WebServlet("/SendNowTrading")
public class SendNowTrading extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendNowTrading() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub.

		// 데이터 인코딩 설정
	    request.setCharacterEncoding("utf-8");
	    response.setContentType("text/html;charset=utf-8");
	    
	    // cors 해결
	    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        response.setHeader("Access-Control-Allow-Origin", "*");
		
        HttpSession session = request.getSession();
		System.out.println(session.getId());

    	ArrayList<TradingElement> nT = new ArrayList<TradingElement>();
    	nT.clear();
    	nT.add(new TradingElement("s", "s", "s", 1.1, "s", new Date(), new Date(), 1.1));
    	nT.add(new TradingElement("s", "s", "s", 2.2, "s", new Date(), new Date(), 2.2));
    	
    	Gson gson = new Gson();
    	String ntJson = gson.toJson(nT);
		   	

		PrintWriter out = response.getWriter();
		out.print(ntJson);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}