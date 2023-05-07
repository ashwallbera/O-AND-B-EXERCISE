<title>Login Page</title>

<div class="container-fluid">
	<h1>Login Page</h1>
	<form action="student_home">
		<strong style="color: red">${student_login_error}</strong><br /> If
		you are a student, enter your <b>student number</b>: <input
			type="text" name="student_number" id="student_number"> <input
			type="submit" value="Submit">
	</form>
</div>

<div class="container-fluid">
	<form action="admin_home">
		<strong style="color: red">${admin_login_error}</strong><br /> If you
		are an administrator, enter your <b>admin ID number</b>: <input
			type="text" name="admin_id" id="admin_id"> <input
			type="submit" value="Submit">
	</form>
</div>
