SELECT DISTINCT c.name
FROM FLIGHTS AS f
JOIN CARRIERS AS c ON f.carrier_id = c.cid
GROUP BY f.carrier_id, f.month_id, f.day_of_month
HAVING COUNT(f.fid) > 1000;
