SELECT sections.section_id, subject_id, room_name,
schedule, sections.faculty_number, firstname, lastname
FROM sections
LEFT OUTER JOIN faculty
ON sections.faculty_number = faculty.faculty_number
WHERE section_id NOT IN 
(SELECT section_id FROM enlistments
WHERE enlistments.student_number = ?)
ORDER BY sections.section_id