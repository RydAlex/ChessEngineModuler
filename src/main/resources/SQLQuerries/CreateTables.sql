CREATE TABLE EngineName(
  ID                                  SERIAL           PRIMARY KEY    NOT NULL,
  engine_name                         VARCHAR(500)     UNIQUE         NOT NULL,
  type_of_game_used_by_that_engine    VARCHAR(100)     UNIQUE         NOT NULL
);

CREATE TABLE CurrentElo(
  ID                                  SERIAL           PRIMARY KEY    NOT NULL,
  elo_value                           INTEGER                         NOT NULL,
  engine_name_id                      INTEGER          REFERENCES     EngineName (ID)
);

CREATE TABLE EloGamesHistory(
  ID                                  SERIAL           PRIMARY KEY    NOT NULL,
  timestamp                           TIMESTAMP                       NOT NULL          DEFAULT CURRENT_TIMESTAMP,
  old_elo                             INTEGER                         NOT NULL,
  is_win                              BOOLEAN                         NOT NULL,
  engine_name_id                      INTEGER          REFERENCES     EngineName (ID)
);

CREATE TABLE Score(
  ID                                  SERIAL           PRIMARY KEY    NOT NULL,
  timestamp                           TIMESTAMP                       NOT NULL          DEFAULT CURRENT_TIMESTAMP,
  score                               INTEGER                         NOT NULL,
  engine_name_id                      INTEGER          REFERENCES     EngineName (ID)
);

CREATE TABLE RandomGameResult(
  ID                                  SERIAL           PRIMARY KEY    NOT NULL,
  timestamp                           TIMESTAMP                       NOT NULL          DEFAULT CURRENT_TIMESTAMP,
  is_win                              BOOLEAN                         NOT NULL,
  engine_name_id                      INTEGER          REFERENCES     EngineName (ID)
);


CREATE TABLE DepthWith2MoreEloUseWinLose(
  ID                                  SERIAL           PRIMARY KEY    NOT NULL,
  timestamp                           TIMESTAMP                       NOT NULL          DEFAULT CURRENT_TIMESTAMP,
  is_win                              BOOLEAN                         NOT NULL,
  engine_name_id                      INTEGER          REFERENCES     EngineName (ID)
);

DROP TABLE DepthWith2MoreEloUseWinLose;
DROP TABLE RandomGameResult;
DROP TABLE Score;
DROP TABLE EloGamesHistory;
DROP TABLE CurrentElo;
DROP TABLE EngineName;
