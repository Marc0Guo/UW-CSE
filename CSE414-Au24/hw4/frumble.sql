-- Q.A
CREATE TABLE Sales
(
    name VARCHAR(255),
    discount VARCHAR(255),
    month VARCHAR(255),
    price int
);



-- Q.B
-- name -> discount
SELECT COUNT(*)
FROM (
    SELECT name
    FROM Sales
    GROUP BY name
    HAVING COUNT(DISTINCT discount) > 1
) AS Sales;
-- Each name has multiple distinct values of discount
-- So that functional dependency does not hold

-- name -> price
SELECT COUNT(*)
FROM (
    SELECT name
    FROM Sales
    GROUP BY name
    HAVING COUNT(DISTINCT price) > 1
) AS Sales;
-- No rows returned
-- name uniquely determines price

-- month → price
SELECT COUNT(*)
FROM (
    SELECT month
    FROM Sales
    GROUP BY month
    HAVING COUNT(DISTINCT price) > 1
) AS Sales;
-- Each month has multiple distinct value of price
-- So that functional dependency does not hold

-- month -> discount
SELECT COUNT(*)
FROM (
    SELECT month
    FROM Sales
    GROUP BY month
    HAVING COUNT(DISTINCT discount) > 1
) AS Sales;
-- No rows returned
-- month uniquely determines discount

-- based on above, we can conclude that functional dependency is
-- name -> price, month -> discount

-- Q.C
-- Decompose into S1(name, price) and S2(discount, month, name)
-- S1 reached the BCNF form.
-- Decompose S2 into S3(month, discount) and R4(month, name))
-- Both S2 and S3 reached BCNF form.

CREATE table S1
(
    name VARCHAR(255) PRIMARY KEY,
    price int
);

CREATE table S3
(
    month VARCHAR(255) PRIMARY KEY,
    discount VARCHAR(255)
);

CREATE table S4
(
    month VARCHAR(255) REFERENCES S3(month),
    name VARCHAR(255) REFERENCES S1(name),
    PRIMARY KEY (month, name)
);


-- Q.D
INSERT INTO S1
SELECT DISTINCT S.name, S.price
FROM Sales AS S;

INSERT INTO S3
SELECT DISTINCT S.month, S.discount
FROM Sales AS S;

INSERT INTO S4
SELECT DISTINCT S.name, S.month
FROM Sales AS S;


SELECT COUNT(*)
FROM S1;
-- 36 rows

SELECT COUNT(*)
FROM S3;
-- 12 rows

SELECT COUNT(*)
FROM S4;
--  426 rows