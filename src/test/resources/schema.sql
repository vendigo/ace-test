CREATE TABLE TUser
(
  name VARCHAR(20),
  age INT,
  entryTime DATETIME DEFAULT now()
);

CREATE TABLE TOldUser
(
  name VARCHAR(20),
  age INT,
  entryTime DATETIME DEFAULT now()
)