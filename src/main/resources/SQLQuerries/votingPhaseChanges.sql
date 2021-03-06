DROP TABLE depthwith2moreelousewinlose;
DROP TABLE randomgameresult;

CREATE TABLE VotingStats(
  ID                                  SERIAL           PRIMARY KEY    NOT NULL,
  vote_pack_name                      varchar(100)                    NOT NULL,
  vote_amount                         INTEGER                         NOT NULL,
  engine_name_id                      INTEGER          REFERENCES     EngineName (ID)
);

DROP INDEX public.enginename_engine_name_key RESTRICT;
ALTER TABLE public.enginename DROP CONSTRAINT enginename_engine_name_key;