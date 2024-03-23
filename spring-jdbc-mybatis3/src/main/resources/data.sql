TRUNCATE TABLE vehicle; -- DROP TABLE vehicle; too recreate the sequence
INSERT INTO vehicle (created_by, created_on, make, mode_year, model, type, vin) VALUES ('AlexiworlD', '2023-11-20T13:42:04.923135', 'toyota', '2023', 'highlander', 'awd', 'vin123');

TRUNCATE TABLE todo;
INSERT INTO todo (todo_title, finished, created_at) VALUES ('ToDo A', TRUE, '2023-11-20T13:42:04.923135');