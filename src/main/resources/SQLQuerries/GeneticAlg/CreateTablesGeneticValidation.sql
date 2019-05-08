CREATE TABLE Cluster(
  ID                    SERIAL            PRIMARY KEY         NOT NULL,
  epoch_number          INTEGER                               NOT NULL,
  elo_score             INTEGER                               NOT NULL
);

CREATE TABLE Engine(
  ID                    SERIAL            PRIMARY KEY         NOT NULL,
  engine_name           VARCHAR(200)      UNIQUE              NOT NULL
);

CREATE TABLE EngineClusterList (
  ID                    SERIAL            PRIMARY KEY         NOT NULL,
  engine_id             INTEGER           REFERENCES                            Engine (ID),
  cluster_id            INTEGER           REFERENCES                            Cluster (ID),
  sequence_no           INTEGER                               NOT NULL
);

CREATE TABLE EloHistory (
  ID                    SERIAL            PRIMARY KEY         NOT NULL,
  elo_value             INTEGER                               NOT NULL,
  games_moves           TEXT                                  NOT NULL,
  timestamp             TIMESTAMP                             NOT NULL,        DEFAULT CURRENT_TIMESTAMP,
  cluster_id            INTEGER           REFERENCES                            Cluster (ID)
)