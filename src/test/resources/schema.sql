CREATE TABLE Country
(
  name VARCHAR(50) NOT NULL UNIQUE,
  capital VARCHAR(50),
  region VARCHAR(50),
  currency VARCHAR(3),
  area BIGINT,
  independenceDay DATE
)

