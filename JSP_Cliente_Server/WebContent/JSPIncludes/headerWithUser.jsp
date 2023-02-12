<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<!-- ======= Header ======= -->
	<header id="header"
		class="fixed-top d-flex align-items-center head-edit"
		style="background-color: #d8d8d8 !important; height: 60px;">
		<div class="container d-flex align-items-center"
			style="max-width: none;">
			<div class="logo mr-auto">
				<a href="../index.html"><img src="../images/logo2.png" alt=""
					class="img-fluid" style="max-height: 50px; border-radius: 6px;"></a>
			</div>
			<nav class="nav-menu d-none d-lg-block" style="float: right;">
				<ul>
					<li class="active"><a href="#" style="font-size: 18px;"><%=session.getAttribute("username")%></a></li>
					<li><a href="#" style="font-size: 18px;">Check Answers</a></li>
					<!--<li class="drop-down log"><a href="#" style="font-size:18px;">Check Answers</a>
						<ul>
							<li><a>User</a></li>
							<li><a>Add content</a></li>
							<li><a>Contact Us</a></li>
						</ul>
					</li>-->
				</ul>
			</nav>
		</div>
	</header>
</body>
</html>