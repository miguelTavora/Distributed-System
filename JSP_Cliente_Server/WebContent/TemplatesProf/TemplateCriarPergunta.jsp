<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>UnioN</title>
<link rel="stylesheet" type="text/css" href="css/main.css" />
<link rel="stylesheet" type="text/css" href="css/styleIndex.css" />
<link rel="stylesheet" type="text/css" href="css/comboBox.css" />
<link rel="stylesheet" type="text/css" href="css/comboBox2.css" />

<!-- Favicons -->
<link href="images/icons/favicon.png" rel="icon" />
<link href="images/icons/apple-touch-icon.png" rel="apple-touch-icon" />

<!-- Google Fonts -->
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Raleway:300,300i,400,400i,500,500i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
	rel="stylesheet"
/>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous"
/>

<!-- j-Query -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<link rel="stylesheet" type="text/css" href="assets/fonts/iconic/css/material-design-iconic-font.min.css" />

<!-- Vendor CSS Files -->
<link href="assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="assets/vendor/icofont/icofont.min.css" rel="stylesheet">

<script src="scripts/comboBox/comboBox.js"></script>
<script src="scripts/comboBox/listBox.js"></script>
<script src="scripts/comboBox/listOpcions.js"></script>

<!-- O nosso css -->
<script src="scripts/main.js"></script>
</head>
<body>
	<!-- ======= Header ======= -->
	<header id="header" class="fixed-top d-flex align-items-center head-edit" style="background-color: #d8d8d8 !important; height: 60px;">
		<div class="container d-flex align-items-center" style="max-width: none;">
			<div class="logo mr-auto">
				<a href="index.html"><img src="images/logo2.png" alt="" class="img-fluid" style="max-height: 50px; border-radius: 6px;"></a>
			</div>
			<nav class="nav-menu d-none d-lg-block" style="float: right;">
				<ul>
					<li><a href="handleReturnQuestProf" style="font-size: 18px;">Return to send questions</a></li>
				</ul>
			</nav>
		</div>
	</header>

	<div class="limiter">
		<div class="container-login100" style="background-image: url('images/bg-01.jpg');">
			<div class="wrap-login100" style="width: 70%;">
				<form class="login100-form validate-form" action="submitNewQuestion" method="POST" onSubmit="return validateNewQuestion()">

					<span class="login100-form-title p-b-34 p-t-27" style="padding-top: 0px; font-size: 45px;">Submit new question</span>
					
					<% if (session.getAttribute("addQuestServer") != null) {%>
					<span class="login100-form-title p-b-34 p-t-27"
						style="margin-top: 30px; margin-bottom: 20px; font-size: 25px;"><%= session.getAttribute("addQuestServer") %></span>
					<%}%>

					<div style="display: flex; align-items: center">
						<label for="theme" style="font-family: Poppins-Medium; color: #fff; font-size: 24px; padding-right: 102px;">Theme:</label>
						<div class="wrap-input100 validate-input" style="width: 50%;">
							<input id="theme" name="theme" class="input100" type="text" placeholder="theme" style="padding: 0px; margin-top: 20px; font-size: 20px;" />
						</div>
					</div>

					<div style="display: flex; align-items: center">
						<label for="question" style="font-family: Poppins-Medium; color: #fff; font-size: 24px; padding-right: 77px;">Question:</label>
						<div class="wrap-input100 validate-input" style="width: 50%;">
							<input id="question" name="question" class="input100" type="text" placeholder="question" style="padding: 0px; margin-top: 20px; font-size: 20px;" />
						</div>
					</div>
					
					<div style="display: flex; align-items: center">
						<label for="question" style="font-family: Poppins-Medium; color: #fff; font-size: 24px; padding-right: 124px;">Time:</label>
						<div class="wrap-input100 validate-input" style="width: 100px;">
							<input id="time-quest" name="time-quest" class="input100" type="text" placeholder="time" style="padding: 0px; margin-top: 20px; font-size: 20px;" />
						</div>
					</div>
					
					
					<!-- Opcoes -->
					<div id="optionsGroup" style="all: unset;">
						<div style="display: flex; align-items: center">
							<label for="opc1" style="font-family: Poppins-Medium; color: #fff; font-size: 24px; padding-right: 96px;">Option 1</label>
							<div class="wrap-input100 validate-input" style="width: 50%;">
								<input id="opc1" name="opc1" class="input100" type="text" style="padding: 0px; margin-top: 20px; font-size: 20px;">
							</div>
							<input id="chx1" name="chx1" type="checkbox" style="float: right; margin: auto;"></input>
						</div>

						<div style="display: flex; align-items: center">
							<label for="opc2" style="font-family: Poppins-Medium; color: #fff; font-size: 24px; padding-right: 91px;">Option 2</label>
							<div class="wrap-input100 validate-input" style="width: 50%;">
								<input id="opc2" name="opc2" class="input100" type="text" style="padding: 0px; margin-top: 20px; font-size: 20px;">
							</div>
							<input id="chx2" name="chx2" type="checkbox" style="float: right; margin: auto;"></input>
						</div>
					</div>

					<div style="display: flex; align-items: center; justify-content: center;padding-top:50px;padding-bottom:50px;">
						<button type="button" id="cb1-button" onclick="addMoreOptions()"
							style="margin: auto; padding: 0em .5em 0em .5em; border-radius: 10px; border: 0px solid transparent; background-color: #E0E0E0; font-size: 20px; height: 35px;"
						>Add more options</button>
						<button type="button" id="cb2-button" onclick="removeOption()"
							style="margin: auto; padding: 0em .5em 0em .5em; border-radius: 10px; border: 0px solid transparent; background-color: #E0E0E0; font-size: 20px; height: 35px;"
						>Remove one option</button>
					</div>


					<!-- Submit button -->
					<div class="container-login100-form-btn">
						<div class="wrap-login100-form-btn">
							<div class="login100-form-bgbtn"></div>
							<button class="login100-form-btn" type="submit" value="Registar">Submit new question</button>
						</div>
					</div>
					<p id="invalid" class="invalid" style="font-size: 16px; color: #e5e5e5; padding-top: 15px" />
				</form>
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
</body>
</html>