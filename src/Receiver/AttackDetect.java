package Receiver;


import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.connection.Dbconn;
import com.mysql.jdbc.Connection;

public class AttackDetect extends Thread {

	int maxx = 50;
	int minn = 5;
	// int aa=0;
	public static int flag = 0;
	public static String AttackYType = null;
	public static ArrayList<String> RecordFound = new ArrayList<String>();
	public static Dbconn db;

	public static Connection con;

	public static void main(String args[]) throws Exception {
		
		 System.out.println("ID=>"+val("192.168.2.9"));

	}

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
			Logger.getLogger(AttackDetect.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		return val1;
	}


}