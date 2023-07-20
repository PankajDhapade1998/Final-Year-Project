<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="java.sql.*"%>
<%@ page import="com.connection.Dbconn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<title>Attacks</title>

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
<!--/head-->
</head>

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
				<li><a href="AdminHome.jsp">Home</a></li>
				<li><a href="FileUpload.jsp">File Upload</a></li>
				<li><a href="FileDownload.jsp">File Download</a></li>
				<li><a href="Owner_Access_Request.jsp">Request Access</a></li>
				<li><a href="ShowAttacks.jsp">Show Attacks</a></li>
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
		<div class="center wow fadeInDown">
			<h2>HOME PAGE</h2>
			<p class="lead">
				Welcome to Home, "<%=session.getAttribute("name")%>"
			</p>

			<table class="table table-responsive table-bordered">

				<tr>
					<td class="col-md-2" align="center"><label>Attacker
							IpAddress</label></td>
					<td class="col-md-4" align="center"><label>MacAddress</label></td>

					<td class="col-md-2" align="center"><label>Image Name</label></td>
					<td class="col-md-2" align="center"><label>Time</label></td>
					<td class="col-md-2" align="center"><label>Download</label></td>
					<td class="col-md-2" align="center"><label>Prevent</label></td>

				</tr>


				<%
					Dbconn db = new Dbconn();
					Connection con = db.conn();
					Statement st1 = con.createStatement();
					String strattck = "select * from attacker where parityflag='0'";
					ResultSet rs1 = st1.executeQuery(strattck);
					while (rs1.next()) {
						String ipadd = rs1.getString("IpAddress");
						String macadd = rs1.getString("MacAddress");
						String imgname = rs1.getString("ImgName");
						String timestam = rs1.getString("time");
						//String imgname=rs1.getString("ImgName");
				%>
				<tr>
					<td class="col-md-2" align="center"><label><%=ipadd%></label></td>
					<td class="col-md-2" align="center"><label><%=macadd%></label></td>
					<td class="col-md-2" align="center"><label><%=imgname%></label></td>
					<td class="col-md-2" align="center"><label><%=timestam%></label></td>
					<br>
					<td class="col-md-4" align="center"><a target="_blank"
						href="ShowImg?image=<%=rs1.getString("ImgName")%>"><input
							type="button" class="btn btn-primary" value="Download"> </a></td>
					<td><a href="PreventClass?ipname=<%=ipadd%>"><input
							type="button" class="btn btn-primary" value="Prevent"></a></td>

				</tr>
				<%
					}
				%>

			</table>

		</div>

		<div class="row">
			<div class="features"></div>
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