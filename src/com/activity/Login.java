package com.activity;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.NetworkInterface;
import java.sql.Timestamp;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sinjection.Authentication;

import com.algo.BruteForce;
import com.connection.Dbconn;
import com.mysql.jdbc.PreparedStatement;

import enscript.EnScript;
import sinjection.Authentication;
import Receiver.AttackDetect;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// public static int hitCount;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();

		//
	}

	public void init() {
		// Reset hit counter.
		Dbconn.hitCount = 0;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();
		session.invalidate();

		out.println("<script type=\"text/javascript\">");
		out.println("alert('You are successfully logged out!');");
		out.println("</script>");
		request.getRequestDispatcher("LoginPage.jsp")
				.include(request, response);
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//
		HttpSession session = request.getSession(true);
		PrintWriter pw = response.getWriter();
		System.out.println("Login Servlet");
		String username = request.getParameter("txt_Uname");
		String Password = request.getParameter("txt_Password");
		System.out.println("Email-ID=>" + request.getParameter("txt_Uname"));
		System.out.println("Password=>" + request.getParameter("txt_Password"));
		String infodata = request.getParameter("infodata");
		int flag = 0;
		Connection con;
		try {
			con = Dbconn.conn();
			if (infodata.equalsIgnoreCase("AdminInfo")) {
				Statement stRegister = con.createStatement();
				ResultSet rsLogin;
				rsLogin = stRegister
						.executeQuery("select * from ownerregistration where Uemail='"
								+ username
								+ "' and Upassword='"
								+ Password
								+ "'");
				if (rsLogin.next()) {
					pw.println("<script> alert('Login Successfully');</script>");
					session.setAttribute("name", rsLogin.getString("Uemail"));
					RequestDispatcher rd = request
							.getRequestDispatcher("/AdminHome.jsp");
					rd.include(request, response);
					flag = 1;
				} else {
					if (Dbconn.hitCount == 2) {
						Dbconn.hitCount++;
						pw.println("<script> alert('User Email & Password Not Matched');</script>");
						RequestDispatcher rd = request
								.getRequestDispatcher("/LoginPage.jsp");
						rd.include(request, response);
						System.out.println("Hit Count=>" + Dbconn.hitCount);
					} else {
						flag = 2;
						Dbconn.hitCount = 1;
					}
				}
			} else {
				Statement stRegister = con.createStatement();
				ResultSet rsLogin;
				rsLogin = stRegister
						.executeQuery("select * from userregistration where Uemail='"
								+ username
								+ "' and Upassword='"
								+ Password
								+ "'");
				if (rsLogin.next()) {
					session.setAttribute("name", rsLogin.getString(1));
					session.setAttribute("email", username);
					RequestDispatcher rd = request
							.getRequestDispatcher("/UserHome.jsp");
					rd.include(request, response);
					flag = 1;

				} else {
					
					if (Dbconn.hitCount == 2) {
						Dbconn.hitCount++;
						pw.println("<script> alert('User Email & Password Not Matched');</script>");
						RequestDispatcher rd = request
								.getRequestDispatcher("/LoginPage.jsp");
						rd.include(request, response);
						System.out.println("Hit Count=>" + Dbconn.hitCount);
					} else {
						flag = 2;
						Dbconn.hitCount = 1;
					}

				}

			}
			if (flag == 2) {
				InetAddress ip = InetAddress.getLocalHost();
				if (AttackDetect.val(ip.getHostAddress().toString()) == 0) {
					BruteForce.CrackPassword(Password);
					if (BruteForce.msg.equals("1")) {
						session.setAttribute("name", username);

						try {

							System.out.println("Current IP address : "
									+ ip.getHostAddress());
							con = Dbconn.conn();
							NetworkInterface network = NetworkInterface
									.getByInetAddress(ip);

							byte[] mac = network.getHardwareAddress();

							System.out.print("Current MAC address : ");

							StringBuilder sb = new StringBuilder();
							for (int ii = 0; ii < mac.length; ii++) {
								sb.append(String.format("%02X%s", mac[ii],
										(ii < mac.length - 1) ? "-" : ""));
							}
							System.out.println(sb.toString());
							Date day = new Date();
							String attack = "CrackAttack";
							String url = request.getRequestURL().toString();

							String sql = "insert into tblattacker values(?,?,?,?)";
							PreparedStatement p = (PreparedStatement) con
									.prepareStatement(sql);
							p.setString(1, attack);
							p.setString(2, ip.getHostAddress().toString());
							p.setString(3, sb.toString());
							p.setString(4, day.toString());
							p.executeUpdate();
							// screen shot
							Toolkit tk = Toolkit.getDefaultToolkit();
							Dimension d = tk.getScreenSize();
							Rectangle rec = new Rectangle(0, 0, d.width,
									d.height);
							Robot ro = new Robot();
							BufferedImage img = ro.createScreenCapture(rec);
							File f = null;
							String nameimg = attack + ".jpg";
							String filename = "F:/" + nameimg;
							String data = attack + "#"
									+ ip.getHostAddress().toString() + "#"
									+ sb.toString() + "#" + day.toString()
									+ "#" + filename;

							f = new File("F:/" + nameimg);// set appropriate
															// path
							if (!f.exists()) {
								f.createNewFile();
							}
							ImageIO.write(img, "jpg", f);
							FileInputStream fis = new FileInputStream(f);
							File ff = new File("F:\\log.txt");
							if (!ff.exists()) {
								ff.createNewFile();

							}

							con = (Connection) Dbconn.conn();
							int paritybit = 0;
							java.util.Date date = new java.util.Date();
							String attacktime = (String.valueOf(new Timestamp(
									date.getTime())));
							String sql1 = "insert into attacker values (?,?,?,?,?,?,?)";
							PreparedStatement pdt1 = (PreparedStatement) con
									.prepareStatement(sql1);
							pdt1.setString(1, ip.getHostAddress().toString());
							pdt1.setString(2, sb.toString());
							pdt1.setString(3, attack);
							pdt1.setString(4, attacktime);

							pdt1.setBinaryStream(5, (InputStream) fis,
									(int) (f.length()));
							pdt1.setString(6, filename);
							pdt1.setInt(7, paritybit);
							int n1 = pdt1.executeUpdate();
							FileWriter fw = new FileWriter(
									ff.getAbsolutePath(), true);
							BufferedWriter bw = new BufferedWriter(fw);

							bw.append(data);
							bw.newLine();
							bw.close();
							fw.close();
						} catch (Exception exc) {
							System.out.println(exc);
						}
					}
					session.setAttribute("email", username);
					RequestDispatcher rd = request
							.getRequestDispatcher("/UserHome.jsp");
					rd.include(request, response);
				}// if end
				else {
					pw.println("<script> alert('Blocked User Access due to user previous attacks');</script>");
					RequestDispatcher rd = request
							.getRequestDispatcher("/LoginPage.jsp");
					rd.include(request, response);
				}
			}// else end
		} catch (Exception e) {
			pw.println("<script> alert(' Unexpected Error');</script>");
			RequestDispatcher rd = request
					.getRequestDispatcher("/LoginPage.jsp");
			rd.include(request, response);
			e.printStackTrace();
		}

	}

}







