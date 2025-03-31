SELECT origin_city,
    (COUNT(CASE WHEN actual_time < 90 THEN 1 END) * 100.0 / COUNT(*)) AS percentage
FROM FLIGHTS
WHERE canceled = 0
GROUP BY origin_city
ORDER BY percentage ASC, origin_city ASC;
