<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<!--META-->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Patient Details</title>
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

	<div class="container"
		style="width: 500px; background: white; margin: 40px auto 80px; border-radius: 5px; padding: 30px">
		<form method="get" id="patient_form" action="PerformServlet">
			<h3 style="margin: 0px -15px 10px; color: #3079ed">Patient's
				details</h3>
			<div class="row" style="margin-bottom: 10px;">
				<div class="col-xs-6">
					<h4>Patient's age</h4>
					<input id="age" name="age" class="form-control" placeholder="Age"
						type="text" />
				</div>
				<div class="col-xs-6">
					<h4>Patient's gender</h4>
					<select id="gender_dropdown" class="form-control" name="gender">
						<option value="1">Male</option>
						<option value="0">Female</option>
					</select>
				</div>
			</div>
			<div class="row" style="margin-bottom: 10px;">
				<div class="col-xs-6">
					<h4>Trest blood pressure</h4>
					<input id="trest_bp" name="trest_bp" class="form-control"
						placeholder="Trest blood pressure" type="text" />
				</div>
				<div class="col-xs-6">
					<h4>Type of chest pain</h4>
					<select id="chest_pain_dropdown" class="form-control" name="chest_pain">
						<option value="1">Normal</option>
						<option value="2">High</option>
						<option value="3">Severe</option>
					</select>
				</div>
			</div>
			<div class="row" style="margin-bottom: 10px">
				<div class="col-xs-6">
					<h4>Cholesterol</h4>
					<input id="cholesterol" name="cholesterol" class="form-control"
						placeholder="Cholesterol" type="text" />
				</div>
				<div class="col-xs-6">
					<h4>Fasting blood sugar</h4>
					<select id="fasting_dropdown" class="form-control" name="fasting_sugar">
						<option value="1">Yes</option>
						<option value="0">No</option>
					</select>
				</div>
			</div>
			<div class="row" style="margin-bottom: 10px;">
				<div class="col-xs-6">
					<h4>Rest ECG</h4>
					<select id="ecg_dropdown" class="form-control" name="rest_ecg">
						<option value="0">Normal</option>
						<option value="1">Wave abnormality</option>
						<option value="2">Definite left hypertrophy</option>
					</select>
				</div>
				<div class="col-xs-6">
					<h4>Exercise including angina</h4>
					<select id="exang_dropdown" class="form-control" name="exang">
						<option value="1">Yes</option>
						<option value="0">No</option>
					</select>
				</div>
			</div>
			<div class="row" style="margin-bottom: 10px;">
				<div class="col-xs-6">
					<h4>Maximum Heart rate</h4>
					<input id="thalach" name="thalach" class="form-control"
						placeholder="Max heart rate" type="text" />
				</div>
				<div class="col-xs-6">
					<h4>Thal</h4>
					<input id="thal" name="thal" class="form-control"
						placeholder="Thal (3 or 6 or 7)" type="text" />
				</div>
			</div>
			<div class="row" style="margin-bottom: 10px">
				<div class="col-xs-6">
					<h4>Weight</h4>
					<input id="weight" name="weight" class="form-control"
						placeholder="Weight in lbs" type="text" />
				</div>
				<div class="col-xs-6">
					<h4>Height</h4>
					<input id="height" name="height" class="form-control"
						placeholder="Height in inches" type="text" />
				</div>
			</div>
			<div class="row" style="margin-botton: 10px; padding-right: 18px;">
				<div class="pull-right">
					<input type="reset" class="btn btn-default" value="Reset"/> <input
						type="submit" class="btn btn-primary" value="Submit" onclick="validateForm()"/>
				</div>
			</div>
		</form>
	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="js/bootstrap.js"></script>
	<script>
		$("#patient_form").submit(function(e){
			e.preventDefault();
		});
		function validateForm()
		{
			var paramValue = document.getElementById('age').value;
			if(isNaN(paramValue) || paramValue.length === 0)
			{
				alert("Age must be in number");
				return false;
			}
			else
			{
				var value = parseInt(paramValue);
				if(!(value > 0 && value < 100))
				{
					alert('Age should be in between 0-100');
					return false;
				}
			}
			
			paramValue = document.getElementById('trest_bp').value;
			if(isNaN(paramValue) || paramValue.length === 0)
			{
				alert("Trest blood pressure must be in number");
				return false;
			}
			
			paramValue = document.getElementById('cholesterol').value;
			if(isNaN(paramValue) || paramValue.length === 0)
			{
				alert("Cholesterol must be in number");
				return false;
			}
			
			paramValue = document.getElementById('thalach').value;
			if(isNaN(paramValue) || paramValue.length === 0)
			{
				alert("Max heart rate must be in number");
				return false;
			}
			
			paramValue = document.getElementById('thal').value;
			if(isNaN(paramValue) || paramValue.length === 0)
			{
				alert("Thal must be in number");
				return false;
			}
			else
			{
				var value = parseInt(paramValue);
				if(!(value==3 || value==6 || value==7))
				{
					alert("Thal value must be 3/5/7");
					return false;
				}
			}
			
			paramValue = document.getElementById('weight').value;
			if(isNaN(paramValue) || paramValue.length === 0)
			{
				alert("Weight must be in number");
				return false;
			}
			
			paramValue = document.getElementById('height').value;
			if(isNaN(paramValue) || paramValue.length === 0)
			{
				alert("Height must be in number");
				return false;
			}
			document.getElementById('patient_form').submit();
		}
	</script>
</body>
</html>