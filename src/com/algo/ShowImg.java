package com.algo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.connection.Dbconn;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class ShowImg
 */
@WebServlet("/ShowImg")
public class ShowImg extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static byte[]  imgdata; 
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowImg() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String imagename=request.getParameter("image");
		System.out.println("image"+imagename);

		Connection con;
		try {
			con = Dbconn.conn();
		 
		Statement st1=con.createStatement();	
	    String strattck="select * from attacker where ImgName='"+imagename+"'";
		ResultSet rs1=st1.executeQuery(strattck);
		while(rs1.next()) 
		{
//			imgdata=rs1.getString("ImgData");
			imgdata = rs1.getBytes("ImgData");
			} 
		
//		String Origional = imgdata;

		response.setHeader("Content-Type", "application/octet-stream");
		response.setContentType("image/jpg");
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ imagename + "\"");
		ServletOutputStream op = response.getOutputStream();
		
		op.write(imgdata);
		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
