SELECT DISTINCT city
FROM (
            SELECT origin_city AS city
        FROM FLIGHTS
    UNION
        SELECT dest_city AS city
        FROM FLIGHTS
) AS all_cities
WHERE city != 'Seattle WA'
    AND city NOT IN (
    -- direct flight
            SELECT dest_city
        FROM FLIGHTS
        WHERE origin_city = 'Seattle WA'
    UNION
        -- one stop
        SELECT f2.dest_city
        FROM FLIGHTS AS f1
            JOIN FLIGHTS AS f2
            ON f1.dest_city = f2.origin_city
        WHERE f1.origin_city = 'Seattle WA'
)
ORDER BY city;
