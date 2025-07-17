SELECT * FROM student;

SELECT * FROM student WHERE age BETWEEN 10 AND 20;

SELECT name FROM student;

SELECT * FROM student WHERE name ILIKE '%о%';

SELECT * FROM student WHERE age < id;

SELECT * FROM student ORDER BY age;

SELECT * FROM pg_indexes WHERE tablename = 'student';

SELECT * FROM pg_indexes WHERE tablename = 'faculty';