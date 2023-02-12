<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html lang="pt">
  <head>
    <title>UnioN</title>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    
	<link href="images/icons/favicon.png" rel="icon">
	<link href="images/icons/apple-touch-icon.png" rel="apple-touch-icon">
	<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="assets/vendor/bootstrap/css/bootstrap.min.css"/><!--alinha icons com texto-->
	<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="assets/fonts/iconic/css/material-design-iconic-font.min.css"/><!--icons-->
	<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="css/main.css" />
	<!--===============================================================================================-->
	<script src="scripts/main.js"></script>
  </head>
  <body>
    <div class="limiter">
      	<div class="container-login100" style="background-image: url('images/bg-01.jpg');">

        <div class="wrap-login100">
          	<form action="handleLoginData" class="login100-form validate-form" method="POST" onSubmit="return validateLogin()">
            <span class="login100-form-logo">
              <i class="zmdi zmdi-accounts-alt"></i>
            </span>

            <span class="login100-form-title p-b-34 p-t-27">
              Teacher
            </span>

            <div class="wrap-input100 validate-input" data-validate="Enter username">
              <input id="username" name="username" class="input100" type="text"  placeholder="Username"/>
              <span class="focus-input100" data-placeholder="&#xf207;"></span>
            </div>

            <div class="wrap-input100 validate-input" data-validate="Enter password">
              <input id="password" name="password" class="input100" type="password" placeholder="Password"/>
              <span class="focus-input100" data-placeholder="&#xf191;"></span>
            </div>

            <div class="container-login100-form-btn">
              <div class="wrap-login100-form-btn">
                <div class="login100-form-bgbtn"></div>
                <button class="login100-form-btn" type="submit" value="Entrar">
                  Login
                </button>
              </div>
            </div>
			<p id="invalid" class="invalid" style="font-size: 16px;color:#e5e5e5;padding-top:15px;padding-bottom:15px;"/>
			<% if (session.getAttribute("usernameError") != null) {  %>
				<h3 style="color:#cccccc;"> The username <%= session.getAttribute("usernameError") %> is not correct</h3>
			<% }%>
            <div class="text-center p-t-90">
              <a class="txt1" href="redirectRegister">
                Don't have an account?  Sign up
              </a>
            </div>
          </form>
        </div>
      </div>
    </div>
    <div id="dropDownSelect1"></div>
  </body>
</html>

