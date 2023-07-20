package com.admininfo;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.algo.AES;
import com.algo.sha256;
import com.connection.Dbconn;

/**
 * Servlet implementation class UserDownload
 */
@WebServlet("/UserDownload")
public class UserDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String origional = "";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserDownload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		byte[] fileBytes = new byte[5000000];
		String fname = request.getParameter("filename");
		HttpSession session = request.getSession(false);
		String fileid=request.getParameter("fileid");
		Connection con;
		ServletOutputStream op = response.getOutputStream();
		String uname = (String) session.getAttribute("name");
		String newKeyfile=request.getParameter("Keyfile");
		String ownername="";
		try {
			con = Dbconn.conn();
			String key = null;
			byte[] bytes = null;
			Statement st1 = con.createStatement();
			ResultSet rsDb1 = st1.executeQuery("select * from tbl_access_info where File_Name='"+ fname + "' and A_id='"+fileid+"'");
			while (rsDb1.next())
			{
				ownername=rsDb1.getString("Owner_Name");
			}
			//
			Statement st = con.createStatement();
			ResultSet rsDb = st
					.executeQuery("select * from tbldata where FileName='"
							+ fname + "' and UserName='"+ownername+"'");
			while (rsDb.next()) {

				key = rsDb.getString("pkey");
				bytes = rsDb.getBytes("C1");
				
			}
			System.out.println("Key=>" + key);
			
			// check key
			int flag=0;
			String shavalues=sha256.applySha256(newKeyfile);
			Statement stt1 = con.createStatement();
			ResultSet rs1 = stt1.executeQuery("select * from tblkey_info where key_enc='"+ shavalues + "'");
			if(rs1.next())
			{
				flag=1;
			}
			else
			{
				flag=0;
			}
			if(flag==1)
			{
			byte[] keys = key.getBytes();
			byte[] dec = AES.decrypt(bytes, keys);
			origional = new String(dec);
			System.out.println("Decrypted Text " + origional);

			response.setHeader("Content-Type", "application/octet-stream");
			response.setContentType("text/plain");
			response.setHeader("Content-Disposition",
					"attachment; filename=\"" + fname + "\"");

			op.println(origional);
			}
			else
			{
				response.sendRedirect("Key_Send_File.jsp?key=1");
			}
			
			
			

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
