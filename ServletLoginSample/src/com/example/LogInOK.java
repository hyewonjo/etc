package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * Servlet implementation class LogInOK
 */
@WebServlet("/LogInOK")
public class LogInOK extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private DataSource dataSource;
	private Connection connection;
	private PreparedStatement stmt;
	private ResultSet resultSet;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogInOK() {
        super();
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
		String name = null;
		String id = null;
		String pw = null;
		String phone1 = null;
		String phone2 = null;
		String phone3 = null;
		String gender = null;
		
		id = request.getParameter("id");
		pw = request.getParameter("pw");
		
		String query = "select * from member where id = ? and pw = ?";
		
		try {
			Context context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/testDB");
			connection = dataSource.getConnection();
			stmt = connection.prepareStatement(query);
			
			stmt.setString(1, id);
			stmt.setString(2, pw);
			
			resultSet = stmt.executeQuery();
			
			if(resultSet.next()) {
				name = resultSet.getString("name");
				id = resultSet.getString("id");
				pw = resultSet.getString("pw");
				phone1 = resultSet.getString("phone1");
				phone2 = resultSet.getString("phone2");
				phone3 = resultSet.getString("phone3");
				gender = resultSet.getString("gender");
				
				HttpSession session = request.getSession();
				session.setAttribute("name", name);
				session.setAttribute("id", id);
				session.setAttribute("pw", pw);
				
				response.sendRedirect("loginResult.jsp");
			}else {
				response.sendRedirect("login.html");
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(resultSet != null) resultSet.close();
				if(stmt != null) stmt.close();
				if(connection != null) connection.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
