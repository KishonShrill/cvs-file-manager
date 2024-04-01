CREATE DATABASE IF NOT EXISTS EnrolledStudents;

USE EnrolledStudents;

CREATE TABLE IF NOT EXISTS Student 
(
	StudentName VARCHAR(255),
	ID CHAR(9),
	YearLevel CHAR(1),
	Gender VARCHAR(20),
	Course VARCHAR(20)
);

-- Note: You can optionally add PRIMARY KEY or UNIQUE constraints to the columns if needed.

-- SELECT statement to query data from the Student table

SELECT *
FROM Student;

LOAD DATA INFILE 'Student.csv' INTO TABLE Student
FIELDS TERMINATED BY ','
IGNORE 1 LINES;
