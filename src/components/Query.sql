CREATE DATABASE IF NOT EXISTS EnrolledStudents;

USE EnrolledStudents;

CREATE TABLE IF NOT EXISTS Student 
(
	StudentName VARCHAR(255),
	ID CHAR(9),
	YearLevel CHAR(1),
	Gender VARCHAR(2),
	Course VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS Course
(
	CourseName VARCHAR(255),
	ID VARCHAR(9)
);

-- Note: You can optionally add PRIMARY KEY or UNIQUE constraints to the columns if needed.
-- SELECT statement to query data from the Student table

SELECT *
FROM Student;

LOAD DATA INFILE 'Student.csv' INTO TABLE Student
FIELDS TERMINATED BY ','
IGNORE 1 LINES;

-- SELECT statement to query data from the Course table

SELECT *
FROM Course;

LOAD DATA INFILE 'Course.csv' INTO TABLE Course
FIELDS TERMINATED BY ','
IGNORE 1 LINES;
