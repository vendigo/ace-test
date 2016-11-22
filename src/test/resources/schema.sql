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
);

CREATE TABLE Country
(
  name VARCHAR(50) NOT NULL UNIQUE,
  capital VARCHAR(50),
  region VARCHAR(50),
  currency VARCHAR(3),
  area BIGINT
)

