<title>Create Section</title>
<div class="container-fluid">
	<h1>Create Section</h1>
	<h3>
		<form action="logout">
			You are logged in as ${firstname} ${lastname}, Admin ID#${admin_id} <input
				class="btn" type="submit" value="Logout" id="logoutButton"
				style="vertical-align: middle; font-size: 70%" />
		</form>
	</h3>

	<div class="row">
		<div class="col-md-12"
			style="border-style: solid; border-radius: 25px">
			<form id="new_section" name="new_section" action="sections"
				method="post">
				<div style="font-weight: bold; font-size: 200%; margin: 1%">
					<c:if test="${not empty errorMessage}">
						<div class="alert alert-warning">${errorMessage}</div>
					</c:if>
					<c:if test="${not empty sectionInfo}">
						<div class="alert alert-success">Section Created:
							${sectionInfo}</div>
					</c:if>
					Section ID: <input type="text" name="section_id" id="section_id"
						style="font-weight: bold; font-size: 120%; margin: 1%">
				</div>
				<div id="choices" class="container-fluid" style="padding:10px">
					<div class="col-md-12">
						<div id="subjects"
							style="float: left; margin-right: 10px; border-style: solid; border-color: gray; border-radius: 25px; padding: 1%">
							<h2>Subjects</h2>
							<c:forEach var="subjectId" items="${subjectIds}">
								<div class="radio">
									<label> <input type="radio" name="subject_id"
										value="${subjectId}" form="new_section">${subjectId}</label>
								</div>
							</c:forEach>
						</div>
						<div id="rooms" style="float: left; margin-right:10px;border-style: solid; border-color: gray; border-radius: 25px; padding: 1%">
							<h2>Rooms</h2>
							<table class="table table-bordered">
								<tr>
									<th></th>
									<th>Room Name</th>
									<th>Capacity</th>
								</tr>
								<c:forEach var="room" items="${rooms}">
									<tr>
										<td><input type="radio" name="room_name"
											value="${room.name}" form="new_section"></td>
										<td>${room.name}</td>
										<td align="center">${room.capacity}</td>
									</tr>
								</c:forEach>
								<tr>
									<td><input type="radio" name="room_name" value="TBA"
										form="new_section"></td>
									<td colspan="2"><strong>TBA</strong></td>
								</tr>
							</table>
						</div>
						<div id="faculty" style="float: left; margin-right:10px;border-style: solid; border-color: gray; border-radius: 25px; padding: 1%">
							<h2>Faculty</h2>
							<table class="table table-bordered">
								<tr>
									<th></th>
									<th>Lastname</th>
									<th>Firstname</th>
									<th>FN#</th>
								</tr>
								<c:forEach var="faculty" items="${faculties}">
									<tr>
										<td><input type="radio" name="faculty_number"
											value="${faculty.facultyNumber}" form="new_section"></td>
										<td>${faculty.lastname}</td>
										<td>${faculty.firstname}</td>
										<td>${faculty.facultyNumber}</td>
									</tr>
								</c:forEach>
								<tr>
									<td><input type="radio" name="faculty_number" value="TBA"
										form="new_section"></td>
									<td colspan="3"><strong>TBA</strong></td>
								</tr>
							</table>
						</div>
						<div id="schedule" class="col-md-3"
							style="border-style: solid; border-color: gray; border-radius: 25px; padding: 10px">
							<h2>Schedule</h2>
							<p>For "TBA", do not select any.</p>
							<div class="container-fluid">
								<div class="row">
									<div id="days" class="col-md-12"
										style="border-style: solid; border-color: gray; border-radius: 25px; margin-bottom: 10px">
										<h3>Days</h3>
										<div class="radio-inline">
											<label> <input type="radio" name="days" value="MTH"
												form="new_section"> MTH
											</label>
										</div>
										<div class="radio-inline">
											<label><input type="radio" name="days" value="TF"
												form="new_section"> TF </label>
										</div>
										<div class="radio-inline">
											<label><input type="radio" name="days" value="WS"
												form="new_section"> WS</label>
										</div>
									</div>
								</div>
								<div class="row">
									<div id="periods" class="col-md-12"
										style="border-style: solid; border-color: gray; border-radius: 25px; margin-top:10px">
										<h3>Periods</h3>
										<div class="radio-inline">
											<label><input type="radio" name="period"
												value="08:30-10:00" form="new_section"> 08:30-10:00
											</label>
										</div>
										<div class="radio-inline">
											<label><input type="radio" name="period"
												value="10:00-11:30" form="new_section"> 10:00-11:30
											</label>
										</div>
										<div class="radio-inline">
											<label><input type="radio" name="period"
												value="11:30-13:00" form="new_section"> 11:30-13:00
											</label>
										</div>
										<div class="radio-inline">
											<label><input type="radio" name="period"
												value="13:00-14:30" form="new_section"> 13:00-14:30
											</label>
										</div>
										<div class="radio-inline">
											<label><input type="radio" name="period"
												value="14:30-16:00" form="new_section"> 14:30-16:00
											</label>
										</div>
										<div class="radio-inline">
											<label><input type="radio" name="period"
												value="16:00-17:30" form="new_section"> 16:00-17:30</label>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div style="float: left; margin: 2%">
							<input type="submit" value="Submit New Section"
								style="font-size: 200%" class="btn" form="new_section"
								onclick="document.new_section.submit()">
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div id="sections_table" class="row">
		<div class="col-md-9">
			<h2>Sections:</h2>
			<table id="sections" class="table table-bordered">
				<tr>
					<th>Section ID</th>
					<th>Subject</th>
					<th>Schedule</th>
					<th>Room</th>
					<th>Faculty</th>
				</tr>
				<c:forEach var="sectionInfo" items="${sectionInfos}">
					<tr>
						<td>${sectionInfo.sectionId}</td>
						<td>${sectionInfo.subjectId}</td>
						<td>${sectionInfo.schedule}</td>
						<td>${sectionInfo.roomName}</td>
						<td>${sectionInfo.faculty}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</div>