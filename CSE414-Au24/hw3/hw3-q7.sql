SELECT DISTINCT c.name AS carrier
FROM CARRIERS c
    JOIN FLIGHTS AS f ON f.carrier_id = c.cid
WHERE f.origin_city = 'Seattle WA'
    AND f.dest_city = 'New York NY'
ORDER BY carrier;
