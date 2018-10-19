
/* Search Best Engines*/
SELECT *
  FROM enginename
    INNER JOIN currentelo ON enginename.id = currentelo.engine_name_id
      WHERE engine_name LIKE '%3000'
      ORDER BY currentelo.elo_value DESC
      LIMIT 4;


/* Search Worst Engines*/
SELECT *
  FROM enginename
    INNER JOIN currentelo ON enginename.id = currentelo.engine_name_id
      WHERE engine_name LIKE '%3000'
      ORDER BY currentelo.elo_value ASC
      LIMIT 4;


SELECT *
  FROM enginename
    INNER JOIN currentelo ON enginename.id = currentelo.engine_name_id
      WHERE engine_name LIKE '%3'
        ORDER BY currentelo.elo_value ASC
