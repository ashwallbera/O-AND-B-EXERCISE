SELECT section_id, subject_id, room_name, schedule,
sections.faculty_number, firstname, lastname FROM sections 
LEFT OUTER JOIN faculty
ON sections.faculty_number = faculty.faculty_number
ORDER BY section_id