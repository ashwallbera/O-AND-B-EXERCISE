CREATE TABLE students (
	student_number SERIAL PRIMARY KEY,
        lastname VARCHAR(35),
	firstname VARCHAR(35)
);
	
CREATE TABLE subjects (
	subject_id VARCHAR(25) PRIMARY KEY,
);

CREATE TABLE rooms (
	room_name VARCHAR(25) PRIMARY KEY,
	capacity INT,
	version INT DEFAULT 0
);

CREATE TABLE faculty (
	faculty_number INT PRIMARY KEY,
	lastname VARCHAR(35),
	firstname VARCHAR(35),
	version INT DEFAULT 0
);

CREATE TABLE sections (
	section_id VARCHAR(25) PRIMARY KEY,
	schedule VARCHAR(25),
	subject_id VARCHAR(25),
	room_name VARCHAR(25),
	faculty_number INT,
	version INT DEFAULT 0,
	FOREIGN KEY (subject_id) REFERENCES subjects (subject_id) DEFERRABLE,
	FOREIGN KEY (room_name) REFERENCES rooms (room_name) DEFERRABLE
);

CREATE TABLE enlistments (
	student_number INT,
	section_id VARCHAR(25),
	PRIMARY KEY (student_number, section_id),
	FOREIGN KEY (student_number) REFERENCES students (student_number) DEFERRABLE,
	FOREIGN KEY (section_id) REFERENCES sections (section_id) DEFERRABLE
);
