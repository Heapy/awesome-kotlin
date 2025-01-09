CREATE TABLE GITHUB_AUTH
(
    ID                       UUID PRIMARY KEY,
    USER_ID                  UUID          NOT NULL,
    ACCESS_TOKEN             VARCHAR(1024) NOT NULL,
    EXPIRES_IN               INT           NOT NULL,
    REFRESH_TOKEN            VARCHAR(1024) NOT NULL,
    REFRESH_TOKEN_EXPIRES_IN INT           NOT NULL,
    TOKEN_TYPE               VARCHAR(1024) NOT NULL,
    SCOPE                    VARCHAR(1024) NOT NULL,
    CREATED_AT               TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP
);
