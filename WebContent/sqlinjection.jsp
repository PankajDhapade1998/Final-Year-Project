<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.activity.*"%>
<%@page import="java.awt.*"%>
<%@page import="java.net.*"%>
<%@page import="java.awt.image.*"%>
<%@page import="java.io.*"%>
<%@page import="javax.imageio.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>
<%@ page import="java.util.Date"%>
<%@page import="com.connection.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.admininfo.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<title>Attack Application</title>

<!-- core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/font-awesome.min.css" rel="stylesheet">
<link href="css/animate.min.css" rel="stylesheet">
<link href="css/prettyPhoto.css" rel="stylesheet">
<link href="css/main.css" rel="stylesheet">
<link href="css/responsive.css" rel="stylesheet">
<!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
    <script src="js/respond.min.js"></script>
    <link rel="shortcut icon" href="images/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="images/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="images/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="images/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="images/ico/apple-touch-icon-57-precomposed.png">
</head><!--/head-->
<body class="homepage">

	<header id="header">
	<div class="top-bar">
		<div class="container">
			<div class="row">
				<div class="col-sm-6 col-xs-4">
					<nav class="navbar navbar-inverse" role="banner">
					<div class="container">
						<div class="navbar-header center wow fadeInDown">
							<button type="button" class="navbar-toggle"
								data-toggle="collapse" data-target=".navbar-collapse">
								<span class="sr-only">Toggle navigation</span> <span
									class="icon-bar"></span> <span class="icon-bar"></span> <span
									class="icon-bar"></span>
							</button>

							<jsp:include page="title.jsp"></jsp:include>
						</div>
					</nav>
					<!--/nav-->
				</div>

			</div>
		</div>
		<!--/.container-->
	</div>
	<!--/.top-bar--> <nav class="navbar navbar-inverse" role="banner">
	<div class="container">

		<div class="collapse navbar-collapse ">
			<ul class="nav navbar-nav">
				<li><a href="UserHome.jsp">Home</a></li>
				<li><a href="sqlinjection.jsp">Search Data</a></li>
				<li><a href="User_Access_File.jsp">Access File</a></li>

				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown"><%=session.getAttribute("name")%><i
						class="fa fa-angle-down"></i></a>
					<ul class="dropdown-menu">
						<li><a href="Login">Logout</a></li>
					</ul></li>


			</ul>
		</div>
	</div>
	<!--/.container--> </nav><!--/nav-->

	</div>
	<!--/.container--> </header>
	<!--/header-->


	<section id="feature">
	<div class="container">

		<div class="row">
			<div class="features">


				<form action="sqlinjection.jsp" method="post">

					<table class="table table-responsive table-bordered">
						<tr>
							<td class="col-md-3">Enter File Name To Search:</td>
							<td class="col-md-5"><input type="text" name="keyword"
								id="keyword" required style="width: 300px" /></td>

						</tr>
						<tr>
							<td class="col-md-6" colspan="2" align="center"><input
								style="width: 100px; height: 30px;" type="submit"
								class="btn-primary" name="Submit" value="Search"> <input
								type="reset" style="width: 100px; height: 30px;"
								class="btn-primary" name="Reset" value="Cancel" /></td>
						</tr>


					</table>



				</form>
				<table class="table table-responsive table-bordered">
					<tr>
						<td class="col-md-2" align="center"><label for="txt_Uname"><h3>File
									Name</h3> </label></td>
						<td class="col-md-2" align="center"><label for="txt_Uname"><h3>Request
									Send</h3> </label></td>
					</tr>
					<%
						String str = request.getParameter("keyword");
						if (str == null || str.isEmpty()) {
							//System.out.println("In Calculate Servlet "+request.getParameter("keyword"));
						} else {
							//System.out.println("In Calculate Servlet "+request.getParameter("keyword"));
							int first = 1;
							Connection conn = Dbconn.conn();
							Double score = 0.10, symptomsscore = 0.0;
							Similarity cs = new Similarity();
							double csInputQuery, csInput;
							Statement st = conn.createStatement();
							Statement stInsert = conn.createStatement();
							int flg = 0;
							ResultSet rs = st
									.executeQuery("select * from tbldata where FileName Like '%"
											+ str + "%'");

							while (rs.next()) {
								flg = 1;

								String filename = rs.getString("FileName");
								String F_id = rs.getString("F_id");
					%>
					<tr>
					<tr>
						<td class="col-md-3" align="center"><label><%=filename%></label></td>
						<td class="col-md-3" align="center"><a
							href="sendkeyrequest?file_id=<%=F_id%>,<%=filename%>"><button
									class="btn btn-primary" name="btn_download" id="btn_download">Send
									Key Request</button></a></td>

					</tr>
					</tr>

					<%
						//System.out.println(" cs score is "+csInputQuery);
								//stInsert.executeUpdate("update tbldata set csscore='"+csInputQuery+"' where FileName='"+rs.getString("FileName")+"' ");

							}
							if (flg == 0) {
								String name = null;
								int i = 0;
								double anomlyscore = 0.40;
								DateFormat dateFormat = new SimpleDateFormat(
										"yyyy/MM/dd HH:mm:ss");
								Calendar cal = Calendar.getInstance();
								System.out.println(dateFormat.format(cal.getTime()));
								String time = dateFormat.format(cal.getTime()).toString();
								//*****************************************
								Statement stat = null;
								stat = (Statement) conn.createStatement();
								stat.executeQuery("select * from signature");
								ResultSet rs1 = null;
								FileInputStream fis;

								rs1 = stat.getResultSet();
								File f = null;

								int jj = 0;
								//***************************************t********
								while (rs1.next()) {
									String currentstring = rs1.getString(1);
									score = cs.similarity(str, currentstring);
									System.out.println("Str=====>" + str);
									System.out.println("Str=====>" + currentstring);
									System.out.println("Score is=====>" + score);
									i++;

									if (score > anomlyscore) {

										try {

											jj = 1;
											int paritybit = 0;
											InetAddress ip = InetAddress.getLocalHost();
											System.out.println("Current IP address : "
													+ ip.getHostAddress());

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

											Toolkit tk = Toolkit.getDefaultToolkit();
											Dimension d = tk.getScreenSize();
											Rectangle rec = new Rectangle(0, 0, d.width,
													d.height);
											Robot ro = new Robot();
											BufferedImage img = ro.createScreenCapture(rec);
											name = "SQL" + i + ".jpg";
											f = new File("F:/" + name);//set appropriate path

											if (!f.exists()) {
												f.createNewFile();
											}
											ImageIO.write(img, "jpg", f);
											fis = new FileInputStream(f);

											String ReceivedData = ip.getHostAddress()
													.toString()
													+ "#"
													+ sb.toString()
													+ "#"
													+ str + "#" + time + "#" + "D:/" + name;
											System.out.println(ReceivedData);
											String Attrack_Name = "SQL_Injection";
											Date day = new Date();

											File ff = new File("E:\\log.txt");
											if (!ff.exists()) {
												ff.createNewFile();
											}
											FileWriter fw = new FileWriter(
													ff.getAbsolutePath(), true);
											BufferedWriter bw = new BufferedWriter(fw);
											String str1 = ReceivedData.replaceAll(
													"[\r\n]+", " ");
											String data = str1.trim();
											bw.append(data);
											bw.newLine();
											bw.close();
											fw.close();

					                          System.out.println(ReceivedData);
					                        conn= Dbconn.conn();
					 						String sql1="insert into tblattacker values(?,?,?,?)";
					 						PreparedStatement p=(PreparedStatement) conn.prepareStatement(sql1);
					 						p.setString(1,Attrack_Name);
					 						p.setString(2,ip.getHostAddress().toString());
					 						p.setString(3,sb.toString());
					 						p.setString(4,day.toString());
					 						p.executeUpdate();
					 						
					                        
					                         String sql="insert into attacker values (?,?,?,?,?,?,?)";
					                         PreparedStatement pdt = conn.prepareStatement(sql);
					                         pdt.setString(1, ip.getHostAddress().toString());
					                         pdt.setString(2, sb.toString());
					                         pdt.setString(3, Attrack_Name);
					                         pdt.setString(4, time);
					                       
					                         pdt.setBinaryStream(5, (InputStream)fis, (int)(f.length())); 
					                         pdt.setString(6, name);
					                         pdt.setInt(7, paritybit);
					                         int n1=pdt.executeUpdate(); 
					                         
					%>

					<%
						break;

										} catch (Exception ex) {
											System.out.println(ex.getMessage());
										}

										/* String user= BuyersLogin.Doc;          
										System.out.println("uaser is"+user); */

									}

								}
								if (jj == 0) {
					%>
					<script>
						alert('Word not matched in database');
					</script>
					<%
						}
							}

						}
					%>
				</table>


			</div>
			<!--/.services-->
		</div>
		<!--/.row-->
	</div>
	<!--/.container--> </section>
	<!--/#feature-->


	<footer id="footer" class="midnight-blue"> </footer>
	<!--/#footer-->

	<script src="js/jquery.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/jquery.prettyPhoto.js"></script>
	<script src="js/jquery.isotope.min.js"></script>
	<script src="js/main.js"></script>
	<script src="js/wow.min.js"></script>
</body>
</html>