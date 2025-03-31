SELECT c.name AS name,
       (100.0 * SUM(f.canceled) / COUNT(f.fid)) AS percentage
FROM FLIGHTS AS f
JOIN CARRIERS AS c ON f.carrier_id = c.cid
WHERE f.origin_city = 'Seattle WA'
GROUP BY c.name
HAVING percentage > 0.5
ORDER BY percentage ASC;