<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>UnioN</title>
<link rel="stylesheet" type="text/css" href="css/main.css" />
<link rel="stylesheet" type="text/css" href="css/styleIndex.css" />

<!-- Favicons -->
<link href="images/icons/favicon.png" rel="icon" />
<link href="images/icons/apple-touch-icon.png" rel="apple-touch-icon" />

<!-- Google Fonts -->
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Raleway:300,300i,400,400i,500,500i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous" />

<!-- j-Query -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<link rel="stylesheet" type="text/css"
	href="assets/fonts/iconic/css/material-design-iconic-font.min.css" />

<!-- Vendor CSS Files -->
<link href="assets/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link href="assets/vendor/icofont/icofont.min.css" rel="stylesheet">


<script src="scripts/main.js"></script>

</head>

<body>
	<!-- ======= Header ======= -->
	<header id="header"
		class="fixed-top d-flex align-items-center head-edit"
		style="background-color: #d8d8d8 !important; height: 60px;">
		<div class="container d-flex align-items-center"
			style="max-width: none;">
			<div class="logo mr-auto">
				<a href="index.php"><img src="images/logo2.png" alt=""
					class="img-fluid" style="max-height: 50px; border-radius: 6px;"></a>
			</div>
			<nav class="nav-menu d-none d-lg-block" style="float: right;">
				<ul>
					<li><a href="handleReloadQuestion" style="font-size: 18px;">Return to Questions</a></li>
				</ul>
			</nav>
		</div>
	</header>

	<div class="limiter">
		<div class="container-login100"
			style="background-image: url('images/bg-01.jpg');">
			<div class="wrap-login100" style="width: 70%;">

				<span class="login100-form-title p-b-34 p-t-27"
					style="padding-top: 0px; font-size: 45px;">Student Information</span>

				<div style="display: flex; align-items: center">
					<label for="first-name"
						style="font-family: Poppins-Medium; color: #fff; font-size: 24px; padding-right: 96px;">First name:</label>
					<div class="wrap-input100 validate-input"
						data-validate="Enter password" style="width: 30%;">
						<input id="first-name" name="first-name" class="input100" type="text"
							placeholder="First name"
							style="padding: 0px; margin-top: 20px; font-size: 20px;" disabled />
					</div>
				</div>

				<div style="display: flex; align-items: center">
					<label for="last-name"
						style="font-family: Poppins-Medium; color: #fff; font-size: 24px; padding-right: 96px;">Last name:</label>
					<div class="wrap-input100 validate-input"
						data-validate="Enter password" style="width: 30%;">
						<input id="last-name" name="last-name" class="input100" type="text"
							placeholder="Last name"
							style="padding: 0px; margin-top: 20px; font-size: 20px;" disabled />
					</div>
				</div>
				
				<div style="display: flex; align-items: center">
					<label for="student-number"
						style="font-family: Poppins-Medium; color: #fff; font-size: 24px; padding-right: 24px;">Student number:</label>
					<div class="wrap-input100 validate-input"
						data-validate="Enter password" style="width: 30%;">
						<input id="student-number" name="student-number" class="input100" type="text"
							placeholder="Student number"
							style="padding: 0px; margin-top: 20px; font-size: 20px;" disabled />
					</div>
				</div>

				<div style="display: flex; align-items: center">
					<label for="birthday"
						style="font-family: Poppins-Medium; color: #fff; font-size: 24px; padding-right: 20px;">Student birthday:</label>
					<div class="wrap-input100 validate-input"
						data-validate="Enter password" style="width: 30%;">
						<input id="birthday" name="birthday" class="input100" type="text"
							placeholder="Birthday"
							style="padding: 0px; margin-top: 20px; font-size: 20px;" disabled />
					</div>
				</div>


			</div>
		</div>
	</div>

	<!-- ======= Footer ======= -->
	<footer id="footer" style="background-color: #bbb">
		<div class="container d-md-flex footerReset"
			style="padding-bottom: 0.6rem !important; padding-top: 0.75rem !important;">
			<div class="mr-md-auto text-center text-md-left"
				style="line-height: 35px; text-align: center;">
				<div class="copyright">&copy; Copyright. All Rights Reserved</div>
			</div>
		</div>
	</footer>

	<script src="scripts/editChoose.js"></script>

	<% if (session.getAttribute("showInfoStudent") != null) {%>
		<%pageContext.getOut().write("" + (session.getAttribute("showInfoStudent")));%>
	<%}%>

</html>