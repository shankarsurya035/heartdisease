<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Decide First</title>
<link type="text/css" rel="stylesheet" href="css/bootstrap.min.css" />
<style>
body {
	height: 100%;
	margin: 0;
	background: -webkit-gradient(linear, left top, left bottom, from(#2576B7),
		to(#043256));
	background-repeat: no-repeat;
	background-attachment: fixed;
}

.myclass {
	width: 200px;
	margin: 80px auto 80px;
	background: white;
	border-radius: 5px;
	padding-left: 50px;
	padding-right: 50px;
	padding-top: 30px;
	padding-bottom: 30px;
}
</style>
</head>
<body>
	<div class="myclass">
		<div class="row">
			<a href="welcome.jsp" class="btn btn-large btn-warning btn-block">Checkup</a>
		</div>
		<br>
		<div class="row">
			<a href="history.jsp" class="btn btn-large btn-warning btn-block">History</a>
		</div>
	</div>
</body>
</html>