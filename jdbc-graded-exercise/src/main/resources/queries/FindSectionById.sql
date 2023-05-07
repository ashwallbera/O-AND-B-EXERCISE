SELECT sections.subject_id, sections.schedule, sections.faculty_number, enlistments.student_number,
sections.room_name, sections.version, firstname, lastname, capacity FROM sections
LEFT OUTER JOIN rooms 
ON sections.room_name = rooms.room_name
LEFT OUTER JOIN faculty
ON sections.faculty_number = faculty.faculty_number
LEFT OUTER JOIN enlistments
ON sections.section_id = sections.section_id
WHERE sections.section_id = ?
