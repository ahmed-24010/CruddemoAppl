CREATE USER IF NOT EXISTS 'springstudent'@'localhost' IDENTIFIED BY 'springstudent';
GRANT ALL PRIVILEGES ON `student_tracker`.* TO 'springstudent'@'localhost';
FLUSH PRIVILEGES;
