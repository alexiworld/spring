CREATE TABLE vehicle IF NOT EXISTS
(
    id SERIAL PRIMARY KEY,
    created_by VARCHAR(255),
    created_on TIMESTAMP,
    make VARCHAR(255),
    mode_year VARCHAR(255),
    model VARCHAR(255),
    type VARCHAR(255),
    vin VARCHAR(255)
);

CREATE TABLE todo IF NOT EXISTS (
    todo_id SERIAL PRIMARY KEY,
    todo_title VARCHAR(30),
    finished BOOLEAN,
    created_at TIMESTAMP
);
