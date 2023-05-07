ALTER TABLE students ADD middle_name TEXT;
ALTER TABLE faculty ADD middle_name TEXT;
UPDATE students SET middle_name ='Jojo' WHERE student_number = 4;
UPDATE faculty SET middle_name ='Edward' WHERE faculty_number = 4;

