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
	rel="stylesheet">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous" />
	
<!-- j-Query -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	
<link rel="stylesheet" type="text/css"
	href="assets/fonts/iconic/css/material-design-iconic-font.min.css" />

<!-- Vendor CSS Files -->
<link href="assets/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link href="assets/vendor/icofont/icofont.min.css" rel="stylesheet">

<script src="scripts/comboBox/comboBox.js"></script>
<script src="scripts/comboBox/listBox.js"></script>
<script src="scripts/comboBox/listOpcions.js"></script>
<script src="scripts/main.js"></script>

</head>

<body>
	<div class="limiter">
		<div class="container-login100" style="background-image: url('images/bg-01.jpg');">
			<div class="wrap-login100">
				<form action="handleNewProf" class="login100-form validate-form" method="POST" onSubmit="return validateNewProf()">

					<span class="login100-form-logo"> <i class="zmdi zmdi-accounts-alt"></i>
					</span> <span class="login100-form-title p-b-34 p-t-27"> New Teacher </span>
					
					<% if (session.getAttribute("registerResult") != null) {%>
					<span class="login100-form-title p-b-34 p-t-27"
						style="margin-top: 30px; margin-bottom: 20px; font-size: 25px;"><%= session.getAttribute("registerResult") %></span>
					<%}%>

					<div class="wrap-input100 validate-input" data-validate="Enter username">
						<input id="username" name="username" class="input100" type="text" placeholder="New Username" /> <span class="focus-input100" data-placeholder="&#xf207;"></span>
					</div>

					<div class="wrap-input100 validate-input" data-validate="Enter password">
						<input id="password" name="password" class="input100" type="password" placeholder="Password"  /> <span class="focus-input100" data-placeholder="&#xf191;"></span>
					</div>

					<div class="wrap-input100 validate-input" data-validate="Verify password">
						<input id="verify_password" name="verify_password" class="input100" type="password" placeholder="Confirm password" /> <span class="focus-input100"
							data-placeholder="&#xf191;"
						></span>
					</div>
					
					<div class="wrap-input100 validate-input" data-validate="Verify password">
						<input id="credential" name="credential" class="input100" type="password" placeholder="Access credential" /> <span class="focus-input100"
							data-placeholder="&#xf158;"
						></span>
					</div>

					<div class="container-login100-form-btn">
						<div class="wrap-login100-form-btn">
							<div class="login100-form-bgbtn"></div>
							<button class="login100-form-btn" type="submit" value="Registar">Register</button>
						</div>
					</div>

					<p id="invalid" class="invalid" style="font-size: 16px; color: #e5e5e5; padding-top: 15px" />
					<div class="text-center p-t-90">
						<a class="txt1" href="TemplatesProf/TemplateLoginProf.jsp"> Don't have an account? Sign up </a>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>