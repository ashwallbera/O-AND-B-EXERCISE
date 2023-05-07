SELECT faculty.firstname, faculty.lastname, faculty.version,
sections.section_id, sections.subject_id, sections.schedule,rooms.room_name, rooms.capacity
FROM   faculty
INNER JOIN  sections
ON faculty.faculty_number = sections.faculty_number
INNER JOIN  rooms
ON sections.room_name = rooms.room_name
WHERE  faculty.faculty_number = ?