package com.algo;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.connection.Dbconn;
/**
 * Servlet implementation class PreventClass
 */
@WebServlet("/PreventClass")
public class PreventClass extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PreventClass() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ipadd=request.getParameter("ipname");
		System.out.println("IP Address"+ipadd);
		int a = 1;
		DecimalFormat df = new DecimalFormat("#.##");
		TreeSet<String> al = new TreeSet<String>();
		ArrayList<String> training = new ArrayList<String>();
//		NB nb = new NB();
		int count = 0;
		try {
			Dbconn dbb = new Dbconn();
			Connection cone = dbb.conn();
			Statement st = cone.createStatement();
			Statement st0 = cone.createStatement();
			Statement st1 = cone.createStatement();
			Statement st2 = cone.createStatement();
			ResultSet rs2 = st2.executeQuery("select * from tbltraining");
			while (rs2.next()) {
				training.add(rs2.getString("trainingdata"));

			}
			ResultSet rs = st.executeQuery("select * from tblattacker");
			while (rs.next()) {
				al.add(rs.getString("IpAddress"));
				count++;
			}
			Iterator itr = al.iterator();
			while (itr.hasNext()) {
				double w1 = 0,w2=0;
				int srcCount=0;
				
				String IpAddress = itr.next().toString();
				System.out.println(IpAddress);
				ResultSet rs1 = st
						.executeQuery("select * from tblattacker where IpAddress='"
								+ IpAddress + "' ");
				while (rs1.next()) {
					String Attack_Type = rs1.getString("Attack_Type");
					for (int i = 0; i < training.size(); i++) {

						String CurrentRule = training.get(i);
//						w1 = w1 + nb.similarity(Attack_Type, CurrentRule);
//						String score = df.format(w1);
//						w2 = Double.parseDouble(score);
						if(Attack_Type.toLowerCase().contains(CurrentRule.toLowerCase()))
				         {
				            srcCount++;
				         }

					}
					
				}
				double weight = (double) ((srcCount*100)/count);
				//System.out.println(w2+"S=" + scores + "Count=>" + count);
				
				
				 System.out.println("IpAddress=>"+IpAddress+"Score=>"+weight);
				if (weight > 15) {
					String qry = "Update attacker set parityflag='" + a
							+ "'where IpAddress='" + IpAddress + "'";
					st0.executeUpdate(qry);
				}
			}
			// String
			// qry="Update attacker set parityflag='"+a+"'where IpAddress='"+ipadd+"'";
			// sttt.executeUpdate(qry);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
				
		RequestDispatcher rd = request.getRequestDispatcher("/ShowAttacks.jsp");
		rd.include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
