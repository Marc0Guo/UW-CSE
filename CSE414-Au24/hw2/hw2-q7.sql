SELECT SUM(f.capacity) AS capacity
FROM FLIGHTS f
JOIN MONTHS m ON f.month_id = m.mid
JOIN WEEKDAYS w ON f.day_of_week_id = w.did
WHERE ((f.origin_city = 'Seattle WA' AND f.dest_city = 'San Francisco CA')
OR (f.origin_city = 'San Francisco CA' AND f.dest_city = 'Seattle WA'))
AND m.month = 'July'
AND f.day_of_month = 10;
