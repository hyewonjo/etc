package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ModifyOK
 */
@WebServlet("/ModifyOK")
public class ModifyOK extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection connection;
	private Statement stmt;
	
	private String name, id, pw, phone1, phone2, phone3, gender;
	
	HttpSession session;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyOK() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		actionDo(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		actionDo(request, response);
	}

	private void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		session = request.getSession();
		
		name = request.getParameter("name");
		id = (String)session.getAttribute("id");
		pw = request.getParameter("pw");
		phone1 = request.getParameter("phone1");
		phone2 = request.getParameter("phone2");
		phone3 = request.getParameter("phone3");
		gender = request.getParameter("gender");
		
		if(pwConfirm()) {
			System.out.println("OK");
			
			String query = "update member set name = '" + name + "', phone1 = '" + phone1 + "', phone2 = '" + phone2 + "', phone3 = '" + phone3
					+ "', gender = '" + gender +"' where id = '" + id + "'";
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8", "root", "root");
				stmt = connection.createStatement();
				int i = stmt.executeUpdate(query);
				if(i == 1) {
					System.out.println("update success");
					session.setAttribute("name", name);
					response.sendRedirect("modifyResult.jsp");
				}else {
					System.out.println("update fail");
					response.sendRedirect("modify.jsp");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				try {
					if(stmt != null) stmt.close();
					if(connection != null) connection.close();
				}catch(SQLException e) {}
			}
		}else {
			System.out.println("NG");
			response.sendRedirect("modify.jsp");
		}
	}

	private boolean pwConfirm() {
		boolean rs = false;
		
		String sessionPw = (String)session.getAttribute("pw");
		
		if(sessionPw.equals(pw))
			rs = true;
		else
			rs = false;
		
		return rs;
	}

}
