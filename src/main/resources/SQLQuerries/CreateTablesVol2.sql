CREATE TABLE EngineName (
    ID                    SERIAL            PRIMARY KEY         NOT NULL,
    engine_name           VARCHAR(300)      UNIQUE              NOT NULL,
    type_of_game          VARCHAR(300)                          NOT NULL,
    current_elo           INTEGER                               NOT NULL
);

CREATE TABLE Battle (
    ID                    SERIAL            PRIMARY KEY         NOT NULL,
    timestamp             TIMESTAMP                             NOT NULL          DEFAULT CURRENT_TIMESTAMP,
    engine_name_one_id    INTEGER           REFERENCES                            EngineName (ID),
    engine_name_two_id    INTEGER           REFERENCES                            EngineName (ID),
    gameplay_string       VARCHAR(2000)                         NOT NULL,
    win_of_first          BOOLEAN
);

CREATE TABLE EloEnginesHistory (
    ID                    SERIAL            PRIMARY KEY         NOT NULL,
    engine_name_id        INTEGER           REFERENCES                            EngineName (ID),
    old_elo               INTEGER                               NOT NULL,
    current_elo           INTEGER                               NOT NULL
);
