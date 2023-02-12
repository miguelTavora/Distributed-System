<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>UnioN</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<!--===============================================================================================-->
<link href="images/icons/favicon.png" rel="icon">
<link href="images/icons/apple-touch-icon.png" rel="apple-touch-icon">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="assets/vendor/bootstrap/css/bootstrap.min.css" />
<!--alinha icons com texto-->
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="assets/fonts/iconic/css/material-design-iconic-font.min.css" />
<!--icons-->
<link rel="stylesheet" type="text/css" href="css/main.css" />
<script src="scripts/main.js"></script>
</head>
<body>
	<div class="limiter">
		<div class="container-login100"
			style="background-image: url('images/bg-01.jpg');">
			<div class="wrap-login100">
				<form class="login100-form validate-form" action="handleCreateRoom" method="POST" onSubmit="return validateKeyRoom()">
					<span class="login100-form-logo"> <i
						class="zmdi zmdi-accounts-alt"></i></span> <span
						class="login100-form-title p-b-34 p-t-27">Create password
						for room</span>

					<div class="wrap-input100 validate-input"
						data-validate="Enter password">
						<input id="key" name="key" class="input100" type="password" placeholder="Key Password" /> 
						<span class="focus-input100" data-placeholder="&#xf183;"></span>
					</div>

					<div class="contact100-form-checkbox">
						<input class="input-checkbox100" id="ckb1" type="checkbox"
							name="remember-me" /> <label class="label-checkbox100"
							for="ckb1">Remember me </label>
					</div>

					<div class="container-login100-form-btn">
						<div class="wrap-login100-form-btn">
							<div class="login100-form-bgbtn"></div>
							<button class="login100-form-btn">Submit key</button>
						</div>
					</div>
				</form>
					<p id="invalid" class="invalid" style="font-size: 16px; color: #e5e5e5; padding-top: 15px" />
					<% if (session.getAttribute("noSession") != null) {  %>
						<h3 style="color:#cccccc;padding-top:20px;"> The session is not created, please login again</h3>
						<div class="text-center p-t-90">
							<form class="login100-form validate-form" action="handleLogout" method="POST">
								<button type="submit" class="txt1">Click here to login again</button>
							</form>
						</div>
					<% }else if (session.getAttribute("existingKey") != null) {  %>
						<h3 style="color:#cccccc;padding-top:20px;"> The key <%= session.getAttribute("existingKey") %> already exists, please choose another one</h3>
					<% }else if (session.getAttribute("errorKey") != null) {  %>
						<h3 style="color:#cccccc;padding-top:20px;"> The key <%= session.getAttribute("errorKey") %> is not valid</h3>
					<% }%>
			</div>
		</div>
	</div>

	<div id="dropDownSelect1"></div>
</body>
</html>