SELECT c.name AS carrier
FROM CARRIERS AS c
WHERE c.cid IN(
    SELECT DISTINCT f.carrier_id
FROM FLIGHTS AS f
WHERE f.origin_city = 'Seattle WA'
    AND f.dest_city = 'New York NY'
)
ORDER BY carrier;
