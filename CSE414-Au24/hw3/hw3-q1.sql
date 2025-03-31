SELECT DISTINCT 
    f1.origin_city, 
    f1.dest_city, 
    f1.actual_time AS time
FROM FLIGHTS AS f1
INNER JOIN (
    SELECT origin_city, 
        MAX(actual_time) AS max_time
    FROM FLIGHTS
    GROUP BY origin_city
) f2 
ON f1.origin_city = f2.origin_city 
    AND f1.actual_time = f2.max_time
ORDER BY 
    f1.origin_city, 
    f1.dest_city;
    
