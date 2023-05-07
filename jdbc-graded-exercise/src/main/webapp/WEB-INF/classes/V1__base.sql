--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.10
-- Dumped by pg_dump version 9.5.10

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- Name: section_pos(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION section_pos(character varying) RETURNS bigint
    LANGUAGE sql IMMUTABLE
    AS $_$select count(section_id) from sections where section_id <= $1;$_$;


ALTER FUNCTION public.section_pos(character varying) OWNER TO postgres;

--
-- Name: student_pos_by_lastname(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION student_pos_by_lastname(character varying) RETURNS bigint
    LANGUAGE sql IMMUTABLE
    AS $_$select count(lastname) from students where lastname <= $1;$_$;


ALTER FUNCTION public.student_pos_by_lastname(character varying) OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: admins; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE admins (
    id integer NOT NULL,
    lastname character varying(35),
    firstname character varying(35)
);


ALTER TABLE admins OWNER TO postgres;

--
-- Name: admins_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE admins_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE admins_id_seq OWNER TO postgres;

--
-- Name: admins_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE admins_id_seq OWNED BY admins.id;


--
-- Name: enlistments; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE enlistments (
    student_number integer NOT NULL,
    section_id character varying(25) NOT NULL
);


ALTER TABLE enlistments OWNER TO postgres;

--
-- Name: faculty; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE faculty (
    faculty_number integer NOT NULL,
    lastname character varying(35),
    firstname character varying(35),
    version integer DEFAULT 0
);


ALTER TABLE faculty OWNER TO postgres;

--
-- Name: faculty_faculty_number_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE faculty_faculty_number_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE faculty_faculty_number_seq OWNER TO postgres;

--
-- Name: faculty_faculty_number_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE faculty_faculty_number_seq OWNED BY faculty.faculty_number;


--
-- Name: rooms; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE rooms (
    room_name character varying(25) NOT NULL,
    capacity integer,
    version integer DEFAULT 0
);


ALTER TABLE rooms OWNER TO postgres;

--
-- Name: sections; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE sections (
    section_id character varying(25) NOT NULL,
    subject_id character varying(25),
    schedule character varying(25),
    room_name character varying(25),
    faculty_number integer,
    version integer DEFAULT 0
);


ALTER TABLE sections OWNER TO postgres;

--
-- Name: students; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE students (
    student_number integer NOT NULL,
    lastname character varying(35),
    firstname character varying(35)
);


ALTER TABLE students OWNER TO postgres;

--
-- Name: students_student_number_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE students_student_number_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE students_student_number_seq OWNER TO postgres;

--
-- Name: students_student_number_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE students_student_number_seq OWNED BY students.student_number;


--
-- Name: subjects; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE subjects (
    subject_id character varying(25) NOT NULL
);


ALTER TABLE subjects OWNER TO postgres;

--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY admins ALTER COLUMN id SET DEFAULT nextval('admins_id_seq'::regclass);


--
-- Name: faculty_number; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY faculty ALTER COLUMN faculty_number SET DEFAULT nextval('faculty_faculty_number_seq'::regclass);


--
-- Name: student_number; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY students ALTER COLUMN student_number SET DEFAULT nextval('students_student_number_seq'::regclass);


--
-- Data for Name: admins; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY admins (id, lastname, firstname) FROM stdin;
1	Romanova	Natasha
2	Banner	Bruce
3	Xavier	Charles
\.


--
-- Name: admins_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('admins_id_seq', 1000, true);


--
-- Data for Name: enlistments; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY enlistments (student_number, section_id) FROM stdin;
3	HASSTUDENTS
4	HASSTUDENTS
\.


--
-- Data for Name: faculty; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY faculty (faculty_number, lastname, firstname, version) FROM stdin;
1	Prime	Optimus	0
2	Lightyear	Buzz	0
3	Kent	Clark	0
4	Stark	Tony	0
\.


--
-- Name: faculty_faculty_number_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('faculty_faculty_number_seq', 10000, true);


--
-- Data for Name: rooms; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY rooms (room_name, capacity, version) FROM stdin;
AVR1	10	0
MATH105	10	0
IP103	10	0
AS113	10	0
CAPACITY1	1	0
AVR2	10	0
\.


--
-- Data for Name: sections; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY sections (section_id, subject_id, schedule, room_name, faculty_number, version) FROM stdin;
MHX123	MATH11	MTH 08:30-10:00	MATH105	1	0
TFX555	PHILO1	TF 10:00-11:30	AS113	2	0
MHW432	COM1	MTH 08:30-10:00	AVR1	3	0
TFZ321	PHYSICS71	TF 14:30-16:00	IP103	4	0
MHY987	COM1	MTH 11:30-13:00	AVR1	1	0
CAPACITY1	COM1	MTH 11:30-13:00	CAPACITY1	2	0
HASSTUDENTS	COM1	TF 11:30-13:00	AVR1	3	0
NOSTUDENTS	MATH11	TF 08:30-10:00	MATH105	4	0
\.


--
-- Data for Name: students; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY students (student_number, lastname, firstname) FROM stdin;
1	Mouse	Mickey
2	Bunny	Bugs
3	Doo	Scooby
4	Simpson	Bart
\.


--
-- Name: students_student_number_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('students_student_number_seq', 100004, true);


--
-- Data for Name: subjects; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY subjects (subject_id) FROM stdin;
MATH11
PHILO1
COM1
PHYSICS71
KAS1
\.


--
-- Name: admins_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY admins
    ADD CONSTRAINT admins_pkey PRIMARY KEY (id);


--
-- Name: enlistments_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY enlistments
    ADD CONSTRAINT enlistments_pkey PRIMARY KEY (student_number, section_id);


--
-- Name: faculty_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY faculty
    ADD CONSTRAINT faculty_pkey PRIMARY KEY (faculty_number);


--
-- Name: rooms_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rooms
    ADD CONSTRAINT rooms_pkey PRIMARY KEY (room_name);


--
-- Name: sections_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY sections
    ADD CONSTRAINT sections_pkey PRIMARY KEY (section_id);


--
-- Name: students_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY students
    ADD CONSTRAINT students_pkey PRIMARY KEY (student_number);


--
-- Name: subjects_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY subjects
    ADD CONSTRAINT subjects_pkey PRIMARY KEY (subject_id);


--
-- Name: students_lastname_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX students_lastname_idx ON students USING btree (lastname);


--
-- Name: enlistments_section_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY enlistments
    ADD CONSTRAINT enlistments_section_id_fkey FOREIGN KEY (section_id) REFERENCES sections(section_id) DEFERRABLE;


--
-- Name: enlistments_student_number_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY enlistments
    ADD CONSTRAINT enlistments_student_number_fkey FOREIGN KEY (student_number) REFERENCES students(student_number) DEFERRABLE;


--
-- Name: sections_faculty_number_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY sections
    ADD CONSTRAINT sections_faculty_number_fkey FOREIGN KEY (faculty_number) REFERENCES faculty(faculty_number) DEFERRABLE;


--
-- Name: sections_room_name_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY sections
    ADD CONSTRAINT sections_room_name_fkey FOREIGN KEY (room_name) REFERENCES rooms(room_name) DEFERRABLE;


--
-- Name: sections_subject_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY sections
    ADD CONSTRAINT sections_subject_id_fkey FOREIGN KEY (subject_id) REFERENCES subjects(subject_id) DEFERRABLE;


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

