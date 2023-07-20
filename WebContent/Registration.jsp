<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
    <![endif]-->       
    <link rel="shortcut icon" href="images/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="images/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="images/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="images/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="images/ico/apple-touch-icon-57-precomposed.png">
</head><!--/head-->

<body class="homepage">

    <header id="header">
        
        <nav class="navbar navbar-inverse" role="banner">
            <div class="container">
                <div class="navbar-header center wow fadeInDown">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <jsp:include page="title.jsp"></jsp:include>
                        </div>
				
                </div><!--/.container-->
        </nav><!--/nav-->
		
    </header><!--/header-->

   
    <section id="feature" >
        <div class="container">
           <div class="center wow fadeInDown">
                <h2>NEW REGISTRATION</h2>
               
         		<div class="col-md-6 col-md-offset-3">
			<form role="form" action="Registration" method="post">
			<div class="form-group">
			<table class="table table-responsive table-bordered">
			<tr>
				
					<td class="col-md-2"><label for="txt_Uname">Select:</label></td>

					<td><input type="radio"
						name="infodata" required value="UserInfo" id="User" />User &nbsp; &nbsp;&nbsp; &nbsp;
						<input type="radio"	name="infodata" required value="OwnerInfo" id="User" />Owner
						</td>
										</tr>
	       <tr>
			<td class="col-md-2"><label for="txt_Uname">User Name:</label></td>
		    <td class="col-md-6"><input type="text" class="form-control" name="txt_Uname" id="txt_Uname" placeholder="Enter Name" required></td>
		    </tr>
		    <tr>
		    <td class="col-md-2"><label for="txt_Address">Address:</label></td>
		    <td class="col-md-6"><input type="text" class="form-control" name="txt_Address" id="txt_Address" placeholder="Enter Name" required></td>
		    </tr>
		    <tr>
		    <td class="col-md-2"><label for="txt_Email">Email Id:</label></td>
		    <td class="col-md-6"><input type="text" class="form-control" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" name="txt_Email" id="txt_Email" placeholder="Enter Name"required></td>
		    </tr>
		    <tr>
		    <td class="col-md-2"><label for="txt_Number">Mobile No:</label></td>
		    <td class="col-md-6"><input type="text" class="form-control" maxlength="10" pattern="[7-9]{1}[0-9]{9}" name="txt_Number" id="txt_Number" placeholder="Enter Mobile No" required></td>
		    </tr>
		
		    <tr>
			<td class="col-md-2"><label for="txt_Password">Password:</label></td>
			<td class="col-md-6"><input type="password" class="form-control" name="txt_Password" id="txt_Password" placeholder="Enter Set Password"  required></td>
			</tr>
			<tr>
			<td class="col-md-2"><label for="txt_ConPassword">Confirm Password:</label></td>
			<td class="col-md-6"><input type="password" class="form-control" name="txt_ConPassword" id="txt_ConPassword" placeholder="Enter Confirm Password" required></td>
			</tr>
			<tr>
			<td class="col-md-6" colspan="2" align="center" >
			<input type="submit" class="btn-primary" name="btn_Register" id="btn_Register" Value="Register"><br>
			<a class="active" href="LoginPage.jsp">Login User</a></td>
			</tr>
			</table>
			</div>
			</form> 				
			</div>
         
            </div>

        </div><!--/.container-->
    </section><!--/#feature-->




    <section id="bottom">
        <div class="container wow fadeInDown" data-wow-duration="1000ms" data-wow-delay="600ms">
            <div class="row">
                <div class="col-md-3 col-sm-6">
                   
                </div><!--/.col-md-3-->

                <div class="col-md-3 col-sm-6">
                     
                </div><!--/.col-md-3-->

                <div class="col-md-3 col-sm-6">
                      
                </div><!--/.col-md-3-->

                <div class="col-md-3 col-sm-6">
                       
                </div><!--/.col-md-3-->
            </div>
        </div>
    </section><!--/#bottom-->

    <footer id="footer" class="midnight-blue">

    </footer><!--/#footer-->

    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.prettyPhoto.js"></script>
    <script src="js/jquery.isotope.min.js"></script>
    <script src="js/main.js"></script>
    <script src="js/wow.min.js"></script>
</body>
</html>