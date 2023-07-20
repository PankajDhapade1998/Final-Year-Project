package com.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Dbconn {
	public static ArrayList<String> ResList = new ArrayList<String>();
	public static ArrayList<String> filetitle = new ArrayList<String>();
	public static ArrayList<String> offline2list = new ArrayList<String>();
	public static int hitCount=1;
	public static String data = "";
public static String fileimage="F:/";
	public Dbconn() throws SQLException {
		super();
	}
	public static Dbconn db;

	public static Connection con;
	public static int val(String addr) {
		int val1 = 0;
		try {
			Statement stt = null;
			db = new Dbconn();
			con = (Connection) Dbconn.conn();
			stt = (Statement) con.createStatement();
			ResultSet rs = stt
					.executeQuery("select parityflag from attacker where IpAddress='"
							+ addr + "'");
			while (rs.next()) {
				val1 = Integer.parseInt(rs.getString(1));

			}
			con.close();
		} catch (Exception ex) {
			
		}
		return val1;
	}
	public static Connection conn() throws SQLException, ClassNotFoundException {
		Connection con;
		//Load the driver
		Class.forName("com.mysql.jdbc.Driver");
		//Create Connection
		con = DriverManager.getConnection(
				"jdbc:mysql://localhost/brute_force_attack", "root",
				"admin");
		return (con);
	}

	

}
