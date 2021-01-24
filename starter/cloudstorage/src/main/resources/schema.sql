CREATE TABLE IF NOT EXISTS USERS (
  userId INT PRIMARY KEY auto_increment,
  username VARCHAR(20),
  salt VARCHAR,
  password VARCHAR,
  firstname VARCHAR(20),
  lastname VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS NOTES (
    noteId INT PRIMARY KEY auto_increment,
    noteTitle VARCHAR(20),
    noteDescription VARCHAR (1000),
    userId INT,
    foreign key (userId) references USERS(userId)
);

CREATE TABLE IF NOT EXISTS FILES (
    fileId INT PRIMARY KEY auto_increment,
    fileName VARCHAR,
    contentType VARCHAR,
    fileSize VARCHAR,
    userId INT,
    fileData BLOB,
    foreign key (userId) references USERS(userId)
);

CREATE TABLE IF NOT EXISTS CREDENTIALS (
    credentialId INT PRIMARY KEY auto_increment,
    url VARCHAR(100),
    userName VARCHAR (30),
    key VARCHAR,
    password VARCHAR,
    userId INT,
    foreign key (userId) references USERS(userId)
);

INSERT INTO USERS (userid, username, salt, password, firstname, lastname) VALUES
('1', 'user', 'ypHX8w5++uSwvHIGZlx6lw==', '1JQIfs1JvrJoG7gmQGjT/g==', 'Alex', 'Lazarovich'); // password = 123