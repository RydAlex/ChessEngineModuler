CREATE TABLE GAME_TYPE(
  ID             SERIAL      PRIMARY KEY         NOT NULL,
  RULE           TEXT        UNIQUE              NOT NULL
)

CREATE TABLE ENGINE(
   ID             SERIAL      PRIMARY KEY         NOT NULL,
   RULE_ID        INT         REFERENCES     GAME_TYPE(ID),
   NAME           TEXT        UNIQUE              NOT NULL,
   ELO            INT                                     ,
   WIN            INT                                     ,
   LOSE           INT                                     ,
   GAMES_PLAYED   INT
);

CREATE TABLE ENGINES_STATISTIC(
   UUID           INT         PRIMARY KEY         NOT NULL,
   RULE_ID        INT         REFERENCES     GAME_TYPE(ID),
   ENGINE_ID      INT         REFERENCES        ENGINE(ID),
   ELO            INT                                     ,
   WIN            INT                                     ,
   LOSE           INT                                     ,
   GAMES_PLAYED   INT
);
