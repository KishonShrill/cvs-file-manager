CREATE TABLE CombinedTable AS
SELECT 
    student.StudentName, 
    student.ID, 
    student.YearLevel, 
    student.Gender,
    student.CourseID,
    course.CourseName
FROM student
JOIN course
    ON student.CourseID = course.ID;