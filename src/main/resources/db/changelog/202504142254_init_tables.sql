CREATE SCHEMA tt;

CREATE TABLE tt.task_history
(
    id         SERIAL PRIMARY KEY,
    task_id    SERIAL NOT NULL,
    data       JSONB  NOT NULL,
    created_by SERIAL NOT NULL,
    moment     TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);