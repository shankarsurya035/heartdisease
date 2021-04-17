<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*" %>
<%@ page import="test.*" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
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
</style>
</head>
<body>
	<%
		Connection con = Database.getConnection();
		Statement st = con.createStatement();
		String query = "select * from history";
		ResultSet rs = st.executeQuery(query);
	%>
	<div class="container" style="background: white;">
		<div class="table-responsive">
		<table class="table">
			<caption style="text-align:center">History of previous checkup</caption>
			<thead>
				<tr>
					<th>Id</th>
					<th>Age</th>
					<th>Gender</th>
					<th>Trest Blood Pressure</th>
					<th>Chest pain type</th>
					<th>Cholesterol</th>
					<th>Fasting sugar</th>
					<th>Rest ECG</th>
					<th>Exercise Angina</th>
					<th>Max. Heart rate</th>
					<th>Thal</th>
					<th>Weight</th>
					<th>Height</th>
					<th>Probability of Yes(Positive)</th>
					<th>Probability of No(Negative)</th>
					<th>Result</th>
				</tr>
			</thead>
			<tbody>
				<% while(rs.next())
					{
				%>
				<tr>
					<td><%=rs.getInt("id") %></td>
					<td><%=rs.getInt("age") %></td>
					<td><%=rs.getString("gender") %></td>
					<td><%=rs.getInt("trest_bp") %></td>
					<td><%=rs.getString("chest_pain") %></td>
					<td><%=rs.getInt("cholesterol") %></td>
					<td><%=rs.getString("fasting_sugar") %></td>
					<td><%=rs.getString("rest_ecg") %></td>
					<td><%=rs.getString("exercise_angin") %></td>
					<td><%=rs.getInt("max_heart_rate") %></td>
					<td><%=rs.getInt("thal") %></td>
					<td><%=rs.getInt("weight") %></td>
					<td><%=rs.getInt("height") %></td>
					<td><%=rs.getFloat("yes_probability") %></td>
					<td><%=rs.getFloat("no_probability") %></td>
					<td><%=rs.getString("result") %></td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>
		</div>
		<div class="pull-right">
			Go back to main page?<a href="decide.jsp">Click Here</a>
		</div>
	</div>
</body>
</html>