CREATE TABLE feature_flag (
    id serial,
    name varchar NOT NULL UNIQUE,
    enabled boolean NOT NULL,
    updated_at timestamp NOT NULL
);

CREATE INDEX feature_flag_enabled_idx ON feature_flag (enabled);

CREATE TABLE feature_flag_user (
    id serial,
    user_id int NOT NULL,
    feature_flag_id int NOT NULL,
    created_at timestamp NOT NULL,
    CONSTRAINT UC_feature_flag_id_user_id UNIQUE (feature_flag_id, user_id),
    FOREIGN KEY (feature_flag_id) REFERENCES feature_flag(id)
);