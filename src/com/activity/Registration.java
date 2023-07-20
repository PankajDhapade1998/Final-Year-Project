package com.activity;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.connection.Dbconn;
import com.mysql.jdbc.PreparedStatement;

/**
 * Servlet implementation class Registrtion
 */
@WebServlet("/Registration")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Registration() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		String a, b, c, d, e, f;

		a = request.getParameter("txt_Uname");
		System.out.println("UserName" + a);
		b = request.getParameter("txt_Address");
		System.out.println("Address" + b);
		c = request.getParameter("txt_Email");
		System.out.println("EmailId" + c);
		d = request.getParameter("txt_Number");
		System.out.println("MobNo" + d);
		e = request.getParameter("txt_Password");
		System.out.println("Password" + e);
		f = request.getParameter("txt_ConPassword");
		System.out.println("conPassword" + f);

		if (e.endsWith(f)) {
			try {
				Connection con;
				con = Dbconn.conn();
				int i = 0;
				String infodata = request.getParameter("infodata");
				if (infodata.equalsIgnoreCase("OwnerInfo")) {

					String sql = "insert into ownerregistration values(?,?,?,?,?)";
					PreparedStatement p = (PreparedStatement) con
							.prepareStatement(sql);

					p.setString(1, a);
					p.setString(2, b);
					p.setString(3, d);
					p.setString(4, c);
					p.setString(5, e);

					i = p.executeUpdate();
				} else {
					String sql = "insert into userregistration values(?,?,?,?,?)";
					PreparedStatement p = (PreparedStatement) con
							.prepareStatement(sql);

					p.setString(1, a);
					p.setString(2, b);
					p.setString(3, d);
					p.setString(4, c);
					p.setString(5, e);

					i = p.executeUpdate();
				}
				if (i != 0) {
					System.out.println("OK ");
					pw.println("<script> alert(' Register Successfuly');</script>");
					RequestDispatcher rd = request
							.getRequestDispatcher("/LoginPage.jsp");
					rd.include(request, response);
				} else {

					RequestDispatcher rd = request
							.getRequestDispatcher("/Registration.jsp");
					rd.include(request, response);
					// System.out.print(" Wrong ID and Password");

				}
			} catch (Exception exc) {
				RequestDispatcher rd = request
						.getRequestDispatcher("/Registration.jsp");
				rd.include(request, response);
				exc.printStackTrace();
			}
		} else {
			pw.println("<script> alert(' Password and confirm Password not matched');</script>");
			RequestDispatcher rd = request
					.getRequestDispatcher("/Registration.jsp");
			rd.include(request, response);
		}
	}

}
