ALTER TABLE model ADD COLUMN class_name character varying(50) NOT NULL;
ALTER TABLE model DROP COLUMN model;
ALTER TABLE model ADD COLUMN model bytea NOT NULL;