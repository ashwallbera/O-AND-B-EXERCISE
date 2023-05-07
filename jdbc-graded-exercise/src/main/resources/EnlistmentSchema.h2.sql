CREATE TABLE students (
	student_number INT PRIMARY KEY,
    lastname varchar(35),
    firstname varchar(35)
);

CREATE TABLE admins (
	id INT PRIMARY KEY,
    lastname varchar(35),
    firstname varchar(35)
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
    lastname varchar(35),
    firstname varchar(35),
	version INT DEFAULT 0
);

CREATE INDEX ON faculty(lastname);

CREATE TABLE sections (
	section_id VARCHAR(25) PRIMARY KEY,
	subject_id VARCHAR(25),
	schedule VARCHAR(25),	
	room_name VARCHAR(25),
	faculty_number INT,
	version INT DEFAULT 0,
	FOREIGN KEY (subject_id) REFERENCES subjects (subject_id),
	FOREIGN KEY (room_name) REFERENCES rooms (room_name),
	FOREIGN KEY (faculty_number) REFERENCES faculty (faculty_number)
);

CREATE TABLE enlistments (
	student_number INT,
	section_id VARCHAR(25),
	PRIMARY KEY (student_number, section_id),
	FOREIGN KEY (student_number) REFERENCES students (student_number),
	FOREIGN KEY (section_id) REFERENCES sections (section_id)
);