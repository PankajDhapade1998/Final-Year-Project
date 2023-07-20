package com.admininfo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletOutputStream;





import com.admininfo.*;
import com.algo.AES;
import com.connection.Dbconn;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
/**
 * Servlet implementation class userfiledownload
 */
@WebServlet("/userfiledownload")
public class userfiledownload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String origional = "";
	
	public static long size, strat, end, totaltime;
	public static String ContentType = null;
	
    public userfiledownload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletOutputStream op = response.getOutputStream();
		HttpSession session = request.getSession(true);
		String strFileName = request.getParameter("filenamedb");
		String username=(String)session.getAttribute("name");
		System.out.println("-------------------------");
		System.out.println("File Name" + strFileName);
		System.out.println(" Welcome to file Download ");

		Connection conn = null;
		try {
			conn = Dbconn.conn();
			String sql = "SELECT * FROM tbldata WHERE FileName = ? and UserName=?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, strFileName);
			statement.setString(2, username);
			ResultSet result = statement.executeQuery();
			if (result.next()) {

				ContentType = result.getString("ContentType");

			}
			String key = null;
			byte[] bytes = null;
			switch (ContentType) {
			case "text/plain":
				Statement st = conn.createStatement();
				ResultSet rsDb = st
						.executeQuery("select * from tbldata where FileName='"
								+ strFileName + "' and UserName='"+username+"'");
				while (rsDb.next()) {

					key = rsDb.getString("pkey");
					bytes = rsDb.getBytes("C1");
					size = Long.parseLong(rsDb.getString("Size"));
				}
				System.out.println("Key=>" + key);
				byte[] keys = key.getBytes();
				byte[] dec = AES.decrypt(bytes, keys);
				origional = new String(dec);
				
				System.out.println("Decrypted Text " + origional);

				response.setHeader("Content-Type", "application/octet-stream");
				response.setContentType("text/plain");
				response.setHeader("Content-Disposition",
						"attachment; filename=\"" + strFileName + "\"");

				op.println(origional);
				break;
			

			}

		} catch (Exception e) {
			// 
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
