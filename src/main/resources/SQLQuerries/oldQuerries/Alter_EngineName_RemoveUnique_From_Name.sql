
--REMOVE
ALTER TABLE enginename DROP CONSTRAINT enginename_engine_name_key;


--Add
ALTER TABLE enginename
  ADD CONSTRAINT enginename_engine_name_key (engine_name);
