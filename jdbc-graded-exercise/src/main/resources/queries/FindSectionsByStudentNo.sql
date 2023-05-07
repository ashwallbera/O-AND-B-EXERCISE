SELECT sections.section_id, subject_id, room_name,
schedule, sections.faculty_number, firstname, lastname
FROM enlistments
LEFT OUTER JOIN sections
ON enlistments.section_id = sections.section_id
LEFT OUTER JOIN faculty
ON sections.faculty_number = faculty.faculty_number
WHERE enlistments.student_number = ?
ORDER BY sections.section_id