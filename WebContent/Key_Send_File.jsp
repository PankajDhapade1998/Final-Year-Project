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
		<div class="center wow fadeInDown">
			<h2>Key Request Page</h2>
			<p class="lead">
				Welcome to Home, "<%=session.getAttribute("name")%>"
			</p>
			<%
				String name = (String) session.getAttribute("email");

				String key = request.getParameter("key");
				if (key == null) {
			%>

			<form name="form1" action="UserDownload" method="post">
				<table class="table table-responsive table-bordered">

					<%
						String data = request.getParameter("fid");
							String[] file_data = data.split(",");
							String fileid = file_data[0];
							String username = file_data[1];
							String filename = file_data[2];
							session.setAttribute("FileName", filename);
					%>
					<tr>
						<td width="20%">File ID</td>
						<td width="80%"> <input type="text" style="width: 50%; height: 15%"
							readonly="readonly" name="fileid" value="<%=fileid%>"></td>
					</tr>
					<tr>
						<td>User Name</td>
						<td><input style="width: 50%; height: 15%" type="text"
							readonly="readonly" name="username" value="<%=username%>">
						</td>
					</tr>
					<tr>
						<td>File Name</td>
						<td><input type="text" style="width: 50%; height: 15%"
							readonly="readonly" name="filename" value="<%=filename%>">
						</td>
					</tr>
					<tr>
						<td>Enter Key</td>
						<td><input type="text" style="width: 50%; height: 15%"
							required="required" name="Keyfile"></td>
					</tr>
					<tr>
						<td colspan="2" align="center"><input type="submit"
							value="Download"
							style="font-family: monotype corsiva; font-size: 22px; font-weight: bold">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>



				</table>
			</form>
			<%
				} else {
					if (key.equals("0")) {

					} else {
			%>

			<script type="text/javascript">
				alert("Wrong Key Please try again");
			</script>
			<%
				}
				}
			%>

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