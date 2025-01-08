CREATE TABLE github_auth (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    access_token VARCHAR(1024) NOT NULL,
    expires_in INT NOT NULL,
    refresh_token VARCHAR(1024) NOT NULL,
    refresh_token_expires_in INT NOT NULL,
    token_type VARCHAR(1024) NOT NULL,
    scope VARCHAR(1024) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
