<title>Enlistment</title>
<div class="container-fluid">
	<h1>
		<form action="logout">
			Enlistment for ${firstname} ${lastname}, SN#${student_number} <input
				class="btn" type="submit" value="Logout" id="logoutButton"
				style="vertical-align: middle; font-size: 70%" />
		</form>
	</h1>
	<c:if test="${not empty enlistmentExceptionMessage}">
		<div class="alert alert-warning">
			<h2>${enlistmentExceptionMessage}</h2>
		</div>
	</c:if>
	<div class="row">
		<div class="col-md-6">
			<h2>Sections Available for Enlistment</h2>

			<table class="table table-bordered" id="available_table">
				<tr>
					<th>Section ID</th>
					<th>Subject</th>
					<th>Schedule</th>
					<th>Room</th>
					<th>Faculty</th>
					<th></th>
				</tr>
				<c:forEach var="sectionInfo" items="${availableSectionInfos}">
					<tr id="${sectionInfo.sectionId}">
						<td>${sectionInfo.sectionId}</td>
						<td>${sectionInfo.subjectId}</td>
						<td>${sectionInfo.schedule}</td>
						<td>${sectionInfo.roomName}</td>
						<td>${sectionInfo.faculty}</td>
						<td><form action="enlist_section" method="post">
								<input type="hidden" name="section_id"
									value="${sectionInfo.sectionId}"><input class="btn"
									type="submit" value="Enlist">
							</form></td>
					</tr>
				</c:forEach>
			</table>

		</div>
		<div class="col-md-6">
			<h2>Currently Enlisted Sections</h2>
			<table class="table table-bordered" id="enlisted_table">
				<tr>
					<th>Section</th>
					<th>Subject</th>
					<th>Schedule</th>
					<th>Room</th>
					<th>Faculty</th>
					<th></th>
				</tr>
				<c:forEach var="sectionInfo" items="${enlistedSectionInfos}">
					<tr id="${sectionInfo.sectionId}">
						<td>${sectionInfo.sectionId}</td>
						<td>${sectionInfo.subjectId}</td>
						<td>${sectionInfo.schedule}</td>
						<td>${sectionInfo.roomName}</td>
						<td>${sectionInfo.faculty}</td>
						<td><form action="cancel_enlistment" method="post">
								<input type="hidden" name="section_id"
									value="${sectionInfo.sectionId}"><input class="btn"
									type="submit" value="Cancel">
							</form></td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</div>