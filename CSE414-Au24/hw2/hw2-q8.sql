SELECT c.name, SUM(f.departure_delay) AS delay
FROM FLIGHTS f
JOIN CARRIERS c ON f.carrier_id = c.cid
GROUP BY c.name;
