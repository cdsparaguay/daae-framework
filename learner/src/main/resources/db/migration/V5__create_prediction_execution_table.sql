CREATE TABLE prediction_schedule_execution
(
    id serial PRIMARY KEY NOT NULL,
    prediction_schedule_id bigint NOT NULL,
    status varchar(10) NOT NULL,
    date timestamp NOT NULL,
    CONSTRAINT prediction_schedule_execution_prediction_schedule_id_fk FOREIGN KEY (prediction_schedule_id) REFERENCES prediction_schedule (id)
);