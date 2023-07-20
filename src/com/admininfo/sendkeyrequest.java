package com.admininfo;

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
 * Servlet implementation class sendkeyrequest
 */
@WebServlet("/sendkeyrequest")
public class sendkeyrequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sendkeyrequest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		String filedata=request.getParameter("file_id");
		String[] data=filedata.split(",");
		String fileid = data[0];
		String filename=data[1];
		HttpSession session= request.getSession(true);
		String useremail=(String)session.getAttribute("email");
		String OwnerEmail="";
		
	
		try {
			Connection con;
			con = Dbconn.conn();
			int i = 0;
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("select * from tbldata where F_id='"+fileid+"'");
			if(rs.next())
			{
				OwnerEmail=rs.getString("UserName");
				
			}
			Statement st1=con.createStatement();
			ResultSet rs1=st1.executeQuery("select * from tbl_access_info where File_Name='"+filename+"'and User_Name='"+useremail+"'");
			if(rs1.next())
			{
				pw.println("<script type=\"text/javascript\">");  
				pw.println("alert('Request Already Sending.......');");  
				pw.println("</script>");
				request.getRequestDispatcher("/sqlinjection.jsp").include(request, response);
				
			}
			else{
				String Status="Pending";
			String sql = "insert into tbl_access_info(Owner_Name,File_Name,User_Name,Status) values(?,?,?,?)";
			PreparedStatement p = (PreparedStatement) con .prepareStatement(sql);
			p.setString(1, OwnerEmail);
			p.setString(2, filename);
			p.setString(3, useremail);
			p.setString(4, Status);
		
			i = p.executeUpdate();
			}
			if (i != 0) {
			System.out.println("OK ");
			pw.println("<script> alert('Send Key Request Successfuly');</script>");
			RequestDispatcher rd = request.getRequestDispatcher("/sqlinjection.jsp");
			rd.include(request, response);
			} 
		} catch (Exception exc) {
			RequestDispatcher rd = request
					.getRequestDispatcher("/sqlinjection.jsp");
			rd.include(request, response);
			exc.printStackTrace();
		}
	}

}
