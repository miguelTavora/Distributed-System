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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	
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
					<li class="active"><a id="see-info" href="handleCheckDataStudent" style="font-size:18px;">Student <%= session.getAttribute("studentNumber") %></a></li>
					<% if(session.getAttribute("profOnline") != null){%>
						<li><a id="see-correction" href="#" style="font-size:18px;color:#03C04A">Teacher online</a></li>
					<% }else {%>
						<li><a id="see-correction" href="#" style="font-size:18px;color:#ff6666">Teacher offline</a></li>
					<% }%>
					<li><a id="see-correction" href="handleShowCorrection" style="font-size:18px;">Check Answers</a></li>
					<li><a href="handleLogoutStudent" style="font-size:18px;">Logout</a></li>
				</ul>
			</nav>
		</div>
	</header>

	<div class="limiter">
		<div class="container-login100"
			style="background-image: url('images/bg-01.jpg');">
			<div class="wrap-login100" style="width: 70%;">
				<form  id="myForm" class="login100-form validate-form" action="handleSubmitQuestion" method="POST" onSubmit="return validateSubmitStudentQuest()">

					<span class="login100-form-title p-b-34 p-t-27"
						style="padding-top: 0px; font-size: 45px;">Answer Questions</span>
					
					<% if(session.getAttribute("submitError") != null){%>
						<span class="login100-form-title p-b-34 p-t-27"
							style="padding-top: 0px; font-size: 32px;color: #ff6666">Can't submit without a question</span>
					<% } else if(session.getAttribute("existingQuest") != null){%>
						<span class="login100-form-title p-b-34 p-t-27"
							style="padding-top: 0px; font-size: 32px;color: #ff6666">Can't reload with a question to answer</span>
					<% }%>
					<div style="display: flex; align-items: center">
						<label for="theme1"
							style="font-family: Poppins-Medium; color: #fff; font-size: 24px; padding-right: 20px;">Theme:</label>
						<div class="wrap-input100 validate-input"
							data-validate="Enter password" style="width: 30%;">
							<input id="theme1" name="theme1" class="input100" type="text" 
								placeholder="Question theme" style="padding: 0px; margin-top: 20px;font-size: 20px;"
								disabled />
						</div>
					</div>
					
					<div style="display: flex; align-items: center">
						<label for="quest1"
							style="font-family: Poppins-Medium; color: #fff; font-size: 24px; padding-right: 20px;padding-top:5px;">Question:</label>
						<div class="wrap-input100 validate-input"
							data-validate="Enter password" style="width: 80%;margin:0px;">
							<input id="quest1" name="quest1" class="input100" type="text" 
								placeholder="Question" style="padding: 0px;font-size: 20px;"
								disabled />
							
						</div>
					</div>
					
					
					<div style="display: flex; align-items: center;margin-left:11%;">
						<div class="wrap-input100 validate-input"
							data-validate="Enter password" style="width: 90%;">
							<input id="quest2" name="quest2" class="input100" type="text"
								style="padding: 0px; margin-top: 20px;font-size: 20px;" disabled />
						</div>
					</div>
					
					
					<div style="display: flex; align-items: center">
						<label for="question-time"
							style="font-family: Poppins-Medium; color: #fff; font-size: 24px; padding-right: 20px;">Time:</label>
						<div class="wrap-input100 validate-input"
							data-validate="Enter username"
							style="margin-top:20px; width: 21%">

							<input id="question-time" name="question-time" class="input100"
								type="text" placeholder="time in seconds" style="padding: 0px;font-size: 20px;" disabled/>
						</div>
					</div>
					


					<!-- Opções de resposta -->
					<section style="padding: 40px 0;">
					
					
						<div>
							<label for="lab1"
								style="font-family: Poppins-Medium; color: #fff; font-size: 24px;">Opcions</label>
							<label for="check1"
								style="font-family: Poppins-Medium; color: #fff; font-size: 24px; margin-left: 81%">Answers</label>
						</div>
						
						

						<div style="display: flex; align-items: center" id="lab1">
							<label for="op1"
								style="font-family: Poppins-Medium; color: #fff; font-size: 24px; padding-right: 20px;">A:</label>
							<div class="wrap-input100 validate-input"
								data-validate="Enter password" style="width: 80%;">
								<input id="op1" name="op1" class="input100" type="text"
									placeholder="Opcion 1" style="padding: 0px; margin-top: 20px;"
									disabled />
							</div>
							<input type="checkbox" id="op-a" name="op-a" value="a" style="margin-left: 10%;" disabled>
						</div>

						<div style="display: flex; align-items: center">
							<label for="op2"
								style="font-family: Poppins-Medium; color: #fff; font-size: 24px; padding-right: 20px;">B:</label>
							<div class="wrap-input100 validate-input"
								data-validate="Enter password" style="width: 80%;">
								<input id="op2" name="op2" class="input100" type="text"
									placeholder="Opcion 2" style="padding: 0px; margin-top: 20px;"
									disabled />
							</div>
							<input type="checkbox" id="op-b" name="op-b" value="a"
								style="margin-left: 10%;" disabled>
						</div>



						<div style="display: flex; align-items: center">
							<label for="op3"
								style="font-family: Poppins-Medium; color: #fff; font-size: 24px; padding-right: 20px;">C:</label>
							<div class="wrap-input100 validate-input"
								data-validate="Enter password" style="width: 80%;">
								<input id="op3" name="op3" class="input100" type="text"
									placeholder="Opcion 3" style="padding: 0px; margin-top: 20px;"
									disabled />
							</div>
							<input type="checkbox" id="op-c" name="op-c" value="a"
								style="margin-left: 10%;" disabled>
						</div>

						<div style="display: flex; align-items: center">
							<label for="op4"
								style="font-family: Poppins-Medium; color: #fff; font-size: 24px; padding-right: 20px;">D:</label>
							<div class="wrap-input100 validate-input"
								data-validate="Enter password" style="width: 80%;">
								<input id="op4" name="op4" class="input100" type="text"
									placeholder="Opcion 4" style="padding: 0px; margin-top: 20px;"
									disabled />
							</div>
							<input type="checkbox" id="op-d" name="op-d" value="a"
								style="margin-left: 10%;" disabled>
						</div>

						<div style="display: flex; align-items: center">
							<label for="op5"
								style="font-family: Poppins-Medium; color: #fff; font-size: 24px; padding-right: 20px;">E:</label>
							<div class="wrap-input100 validate-input"
								data-validate="Enter password" style="width: 80%;">
								<input id="op5" name="op5" class="input100" type="text"
									placeholder="Opcion 5" style="padding: 0px; margin-top: 20px;"
									disabled />
							</div>
							<input type="checkbox" id="op-e" name="op-e" value="a"
								style="margin-left: 10%;" disabled>
						</div>

						<div style="display: flex; align-items: center">
							<label for="op6"
								style="font-family: Poppins-Medium; color: #fff; font-size: 24px; padding-right: 20px;">F:</label>
							<div class="wrap-input100 validate-input"
								data-validate="Enter password" style="width: 80%;">
								<input id="op6" name="op6" class="input100" type="text"
									placeholder="Opcion 6" style="padding: 0px; margin-top: 20px;"
									disabled />
							</div>
							<input type="checkbox" id="op-f" name="op-f" value="a"
								style="margin-left: 10%;" disabled>
						</div>
					</section>

					<div class="container-login100-form-btn">
						<div class="wrap-login100-form-btn">
							<div class="login100-form-bgbtn"></div>
							<button class="login100-form-btn" type="submit" value="Entrar">
								Submit</button>
						</div>
					</div>
					<p id="invalid" class="invalid"
						style="font-size: 16px; color: #e5e5e5; padding-top: 15px" />
					<div class="text-center p-t-90">
						<a class="txt1" href="handleReloadQuestion" style="font-size:15px;"> Click here to check if there is any new questions </a>
					</div>
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

	<a href="#" class="back-to-top"><i class="icofont-simple-up"></i></a>
	<script src="scripts/editChoose.js"></script>
	
	<% if(session.getAttribute("infoStudent") != null){%>
		<% pageContext.getOut().write("" +(session.getAttribute("infoStudent")));%>
	<% }%>
	
	<% if(session.getAttribute("countTime") != null){%>
		<% pageContext.getOut().write("" +(session.getAttribute("countTime")));%>
	<% }%>
	
</body>

</html>