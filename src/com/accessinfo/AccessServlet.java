package com.accessinfo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.connection.Dbconn;
import com.mysql.jdbc.PreparedStatement;

/**
 * Servlet implementation class AccessServlet
 */
@WebServlet("/AccessServlet")
public class AccessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AccessServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		String fileid = request.getParameter("fid");
		try {
			Connection con;
			con = Dbconn.conn();
			int i = 0;
			Statement st = con.createStatement();
			st.executeUpdate("update tbl_access_info set Status='Accept' where A_id='"
					+ fileid + "'");

			pw.println("<script type=\"text/javascript\">");
			pw.println("alert('Request Accept Successfully');");
			pw.println("</script>");
			request.getRequestDispatcher("/Owner_Access_Request.jsp").include(
					request, response);

		} catch (Exception exc) {

		}
	}

}
