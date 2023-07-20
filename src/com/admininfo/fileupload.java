package com.admininfo;

import java.io.*;
import java.math.BigInteger;
import java.sql.*;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.algo.AES;
import com.algo.sha256;
import com.connection.Dbconn;

/**
 * Servlet implementation class fileupload
 */
@WebServlet("/fileupload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50)
public class fileupload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static long TimeRequired;
	public Boolean repeat;
	public int row = 0;
	public double duplication;
	public String strOriginal = null, EncryptedFile = null;
	// public String PriKey;
	public String pkey, ContentType;
	public String KeyDetails11 = "Privatekey:";
	public long size, starttime, endtime, totaltime, totaltimes;
	public static double score = 0.0;
	Connection conn = null, conn11 = null;

	public long numsplitvalue = 1;
	double TotalLoad = 0;
    public fileupload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	public String keyData() {
		StringBuilder ss = new StringBuilder();
		Random r = new Random();
		char ch;

		for (int i = 0; i < 10; i++) {
			ch = (char) (Math.floor(26 * r.nextDouble() + 65));
			ss.append(ch);
		}

		return ss.toString();

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InputStream inputStream = null;
		PrintWriter pw = response.getWriter();
		HttpSession session = request.getSession(false);
		long firsttime = System.currentTimeMillis();
		
		String username = (String) session.getAttribute("name");
		pkey = keyData();
		Part filePart = request.getPart("txt_search");
		ContentType = filePart.getContentType();
		System.out.println("ContentType=>" + ContentType);
		String fileName = getFileName(filePart);
		session.setAttribute("FILEName", fileName);

		if (filePart != null) {
			System.out.println("Size:-" + filePart.getSize());
			System.out.print("FileName:-" + fileName);
			System.out.println("UserName:-" + username);

			inputStream = filePart.getInputStream();

		}
		size = filePart.getSize();
		try {
			Connection conn = Dbconn.conn();
			Statement stavailable = conn.createStatement();
			ResultSet rsavailable = stavailable
					.executeQuery("select * from tbldata where FileName='" + fileName + "'");
			if (rsavailable.next()) {
				PrintWriter out1 = null;
				response.setContentType("text/html;charset=UTF-8");
				out1 = response.getWriter();
				out1.println("<html><script>alert('File Already Exists');</script><body>");
				out1.println("");
				out1.println("</body></html>");
			} else {
				if (ContentType.equals("text/plain")) {
					strOriginal = getStringFromInputStream(inputStream);
					System.out.println("Origional:-" + strOriginal);
					
				} 
					
				}

				{
					byte[] key = pkey.getBytes();
					byte[] input = strOriginal.toString().getBytes();

					byte[] enc = AES.encrypt(input, key);
					long Lasttime1 = System.currentTimeMillis();
					totaltimes = Lasttime1 - firsttime;
					 Connection con;
					con = Dbconn.conn();
					
					String sql1="insert into tbldata(UserName,C1,FileName,ContentType,Size,pkey) values(?,?,?,?,?,?)";
					//st2.executeUpdate("insert into tbldata values('" + username+ "','"+strOriginal+"','" + fileName + "','"+ContentType+"','"+size+"')");
					PreparedStatement stt = conn.prepareStatement(sql1);
					stt.setString(1, username);
					stt.setBytes(2, enc);
					stt.setString(3, fileName);
				    stt.setString(4, ContentType);
					stt.setLong(5, size);
				    stt.setString(6, pkey);
					stt.executeUpdate();
					// sha256
					String shavalues=sha256.applySha256(pkey);
					String sql="insert into tblkey_info(Username,Filename,key_enc) values(?,?,?)";
					PreparedStatement st = conn.prepareStatement(sql);
					st.setString(1, username);
					st.setString(2, fileName);
				    st.setString(3, shavalues);
					st.executeUpdate();
					
					pw.println("<html><script>alert('File Upload Successfully...');</script><body>");
						pw.println("");
						pw.println("</body></html>");
					}
				

			

		} catch (Exception ex) {

			ex.printStackTrace();
		} finally {
			if (conn != null) {

				try {
					conn.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}

		}

		RequestDispatcher rd = request
				.getRequestDispatcher("/AdminHome.jsp");
		rd.include(request, response);
	}

	public void readWrite(RandomAccessFile raf, BufferedOutputStream bw,
			long numBytes, int serverId, String ServerIp, String FileName)
			throws IOException {

		System.out.println("----1111111111111111111111111111111--- ");

		byte[] buf = new byte[(int) numBytes];
		int val = raf.read(buf);
		if (val != -1) {
			bw.write(buf);
		}
	
	}

	
	String getFileName(Part filePart) {

		String partHeader = filePart.getHeader("content-disposition");
	
		for (String cd : filePart.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String fileName = cd.substring(cd.indexOf('=') + 1).trim()
						.replace("\"", "");
				return fileName.substring(fileName.lastIndexOf('/') + 1)
						.substring(fileName.lastIndexOf('\\') + 1);
				// return cd.substring(cd.indexOf('=') + 1).trim().replace("\"",
				// "") ;
			}

		}

		return null;

	}

	private String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				
				sb.append(line);
				sb.append("\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}
}