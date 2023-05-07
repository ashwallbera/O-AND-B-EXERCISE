SELECT      r.room_name, r.capacity, r.version,
            s.section_id, s.subject_id, s.schedule, s.faculty_number
FROM        rooms r
LEFT JOIN   sections s  ON  r.room_name = s.room_name
WHERE       r.room_name = ?;