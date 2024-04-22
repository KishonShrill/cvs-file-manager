CREATE DATABASE IF NOT EXISTS EnrolledStudents;

USE EnrolledStudents;

CREATE TABLE IF NOT EXISTS course
(
	CourseName 			VARCHAR(255)
  ,ID 					VARCHAR(20) PRIMARY KEY
);

INSERT INTO course(CourseName,ID) VALUES ('Bachelor of Science in Agricultural and Bio system Engineering','BSABE');
INSERT INTO course(CourseName,ID) VALUES ('Bachelor of Science in Agriculture','BSA');
INSERT INTO course(CourseName,ID) VALUES ('Bachelor of Science in Biology','BSBio');
INSERT INTO course(CourseName,ID) VALUES ('Bachelor of Science in Chemical Engineering','BSCE');
INSERT INTO course(CourseName,ID) VALUES ('Bachelor of Science in Education','BSED');
INSERT INTO course(CourseName,ID) VALUES ('Bachelor of Science in Information Technology','BSIT');
INSERT INTO course(CourseName,ID) VALUES ('Bachelor of Science in Mechanical Engineering','BSME');
INSERT INTO course(CourseName,ID) VALUES ('Bachelor of Science in Nursing','BSN');
INSERT INTO course(CourseName,ID) VALUES ('Bachelor of Science in Hotel and Restaurant Management','BSHRM');
INSERT INTO course(CourseName,ID) VALUES ('Bachelor of Science in Computer Science','BSCS');
INSERT INTO course(CourseName,IDenrolledstudents) VALUES ('Bachelor of Business Administration','BBA');

CREATE TABLE IF NOT EXISTS student
(
	StudentName       VARCHAR(199) NOT NULL
  ,ID         			CHAR(9) NOT NULL
  ,YearLevel 			CHAR(1) NOT NULL
  ,Gender     			VARCHAR(2) NOT NULL
  ,CourseID     		VARCHAR(20) NULL
  ,CONSTRAINT fk_student_course FOREIGN KEY (CourseID) REFERENCES Course (ID)
);

INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Isabella Daniel Jones','2021-5629','3','F','BSCS');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('John Daniel Rodriguez','2024-9146','3','M','BSCS');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Emma Elizabeth Martinez','2023-1161','5','NB','BSCE');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Sophia Mia Davis','2021-4702','1','F','BSN');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Ava Joseph Williams','2023-2918','2','M','BSED');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Isabella Christopher Martinez','2023-1750','3','F','BSCS');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Olivia Charlotte Rodriguez','2020-2572','4','F','BSCS');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Alexander Joseph Brown','2022-7262','3','NB','BBA');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Emma Mia Davis','2020-3209','5','NB','BSN');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Michael Daniel Davis','2022-2702','3','M','BSME');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('William DavID Davis','2022-6814','5','NB','BSCE');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Alexander Joseph Martinez','2022-3755','3','M','BSCE');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Alexander Matthew Garcia','2024-0026','2','NB','BSHRM');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Olivia DavID Davis','2020-7354','4','F','BSCS');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Alexander Mia Garcia','2024-9740','1','NB','BSIT');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Olivia Elizabeth Miller','2022-3491','3','F','BSED');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('James Grace Johnson','2022-5997','1','F','BSABE');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('James DavID Johnson','2022-3506','4','M','BSIT');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Michael Christopher Jones','2024-3370','5','M','BSCS');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Emma Mia Johnson','2024-6297','5','NB','BSN');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Michael Mia Brown','2024-4392','2','NB','BBA');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Emma Christopher Jones','2022-4690','1','NB','BSABE');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('William Daniel Garcia','2024-9367','2','M','BBA');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Olivia Charlotte Rodriguez','2022-0196','4','M','BSCE');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Olivia DavID Smith','2020-8184','1','M','BSHRM');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('William Christopher Williams','2020-9058','1','F','BSIT');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Isabella Joseph Jones','2020-8949','2','NB','BSA');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('John Mia Williams','2024-6283','1','NB','BBA');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Emma Amelia Smith','2020-3186','4','M','BSED');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('William Christopher Smith','2020-0915','5','F','BSME');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Sophia Christopher Brown','2023-9945','5','NB','BSCE');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('John Amelia Martinez','2022-8986','3','NB','BSME');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Michael Joseph Smith','2020-5104','2','F','BSHRM');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Michael Amelia Davis','2023-3667','4','M','BSIT');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('John Matthew Jones','2024-0212','2','NB','BSN');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Isabella Daniel Garcia','2022-0089','1','M','BSABE');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Olivia Joseph Smith','2024-5361','5','NB','BSABE');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Ava Mia Miller','2024-3331','4','NB','BSCS');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Olivia DavID Johnson','2022-4395','2','NB','BSA');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Isabella Joseph Jones','2020-6319','2','M','BSA');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Ava Matthew Martinez','2021-6551','4','F','BSA');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('John Elizabeth Jones','2022-2726','5','M','BSED');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Michael Matthew Smith','2024-3168','3','F','BSCS');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Michael Matthew Martinez','2020-4891','4','M','BBA');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Isabella Elizabeth Garcia','2024-6953','1','F','BSCS');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Sophia Charlotte Brown','2020-3923','2','NB','BSA');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('James DavID Smith','2022-5016','2','NB','BSABE');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Alexander Elizabeth Jones','2024-1179','3','M','BSME');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('James Mia Brown','2020-8409','2','M','BSA');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Emma Christopher Jones','2024-1724','5','F','BSIT');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Emma Mia Garcia','2020-7800','1','NB','BSA');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('James Christopher Davis','2020-4679','1','M','BSED');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Ava Matthew Martinez','2022-9365','2','M','BSCE');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Emma Amelia Smith','2021-3899','4','M','BSME');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Olivia Matthew Johnson','2020-3314','3','NB','BSN');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Sophia Mia Johnson','2023-2324','5','NB','BSABE');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Emma Amelia Martinez','2021-4589','3','M','BSIT');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Emma Grace Martinez','2021-0918','4','M','BSABE');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Ava Amelia Garcia','2020-2331','4','F','BSME');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Ava Daniel Brown','2022-9100','5','F','BSA');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Olivia Matthew Rodriguez','2022-7564','2','M','BSN');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Alexander Christopher Brown','2021-8930','1','M','BSABE');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Olivia Mia Davis','2020-6636','4','NB','BSED');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('William DavID Jones','2024-9278','4','NB','BSA');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Sophia Daniel Miller','2021-6434','3','F','BSN');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('William Grace Martinez','2020-7344','3','NB','BSABE');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Alexander Joseph Martinez','2020-0363','5','M','BSA');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Alexander Amelia Smith','2023-6058','1','F','BSABE');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('William Elizabeth Johnson','2020-7193','3','F','BSABE');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Olivia Elizabeth Brown','2024-5090','2','M','BSED');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Michael Mia Jones','2020-0471','3','F','BSED');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Ava Joseph Rodriguez','2021-1971','2','F','BSCS');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Isabella Joseph Rodriguez','2021-9320','3','M','BSCE');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Emma Mia Davis','2021-2692','3','NB','BSA');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Emma Amelia Martinez','2024-9295','2','F','BBA');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Olivia Joseph Martinez','2021-6951','2','NB','BSABE');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('James Amelia Jones','2021-7718','1','NB','BSED');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('William Mia Johnson','2022-7850','4','F','BSCE');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('John Matthew Williams','2024-6115','2','NB','BSHRM');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Sophia Charlotte Johnson','2022-7970','4','F','BSN');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Emma Matthew Smith','2024-3773','4','F','BBA');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Olivia Daniel Davis','2024-0752','3','NB','BBA');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Michael Joseph Smith','2023-1400','4','F','BBA');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Sophia DavID Davis','2020-0988','2','NB','BSCE');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Alexander Grace Brown','2023-5254','4','F','BSABE');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Alexander Joseph Johnson','2021-7590','5','M','BSN');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('James Mia Smith','2023-0354','3','M','BSN');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Sophia Mia Williams','2021-7588','2','NB','BBA');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Sophia Christopher Rodriguez','2023-9647','5','NB','BSME');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Alexander Charlotte Miller','2023-4116','3','NB','BSME');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Michael Joseph Miller','2022-0719','2','NB','BSN');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Ava DavID Brown','2022-9571','1','M','BBA');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Olivia Amelia Brown','2023-1145','1','NB','BSIT');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('William Daniel Smith','2024-1972','4','M','BSIT');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Ava Charlotte Jones','2024-3722','5','NB','BSA');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('James Grace Johnson','2024-3891','5','F','BSA');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('John Joseph Johnson','2023-1747','5','F','BSA');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Michael Mia Johnson','2022-6839','3','NB','BSME');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('James Matthew Jones','2023-6422','2','NB','BSME');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('James Elizabeth Jones','2020-6487','2','NB','BSCS');
INSERT INTO student(StudentName,ID,YearLevel,Gender,CourseID) VALUES ('Chriscent Pingol','2022-0362','2','M','BSA');

