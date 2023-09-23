-- liquibase formatted sql

-- changeset olesyalyahor:1
CREATE TABLE notification_task (
    id SERIAL PRIMARY KEY,
    chat_id BIGINT,
    date_time DATETIME,
    text TEXT
    );




