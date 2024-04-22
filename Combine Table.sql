#CREATE TABLE CombinedTable AS
SELECT 
    student.StudentName, 
    student.ID, 
    student.YearLevel, 
    student.Gender,
    student.CourseID,
    course.CourseName
FROM student
LEFT JOIN course
    ON student.CourseID = course.ID

UNION

SELECT 
    student.StudentName, 
    student.ID, 
    student.YearLevel, 
    student.Gender,
    student.CourseID,
    NULL AS CourseName
FROM student
WHERE student.CourseID IS NULL;
