--************************************** [parameters]

CREATE TABLE parameter
(
  id   SERIAL NOT NULL ,
  value VARCHAR(50) NOT NULL ,
  code VARCHAR(50) NOT NULL ,
  domain VARCHAR(50) NOT NULL ,
  "order" INT,

  CONSTRAINT PK_parameter PRIMARY KEY (id )
);



--************************************** [procedure]

CREATE TABLE procedure
(
  id          SERIAL NOT NULL ,
  name        VARCHAR(50) NOT NULL ,
  description VARCHAR(200) NOT NULL ,
  class_name  VARCHAR(50) NOT NULL ,
  target      VARCHAR(50) NOT NULL ,
  type        VARCHAR(50) NOT NULL ,

  CONSTRAINT PK_procedure PRIMARY KEY (id )
);



--************************************** [algorithm]

CREATE TABLE algorithm
(
  id          SERIAL NOT NULL ,
  name        VARCHAR(50) NOT NULL ,
  class_name  VARCHAR(50) NOT NULL ,
  description VARCHAR(200) NOT NULL ,
  viewer      BOOLEAN NOT NULL DEFAULT FALSE,
  norm        BOOLEAN NOT NULL DEFAULT TRUE,
  status      VARCHAR(50) NOT NULL ,
  type        VARCHAR(50) NOT NULL ,

  CONSTRAINT PK_algorithm PRIMARY KEY (id )
);



--************************************** [normalization_parameter]

CREATE TABLE normalization_parameter
(
  id           SERIAL NOT NULL ,
  name         VARCHAR(100) NOT NULL ,
  field_name   VARCHAR(100) NOT NULL,
  procedure_id BIGINT NOT NULL ,

  CONSTRAINT PK_normalization_parameter PRIMARY KEY (id ),
  CONSTRAINT FK_normalization_parameter_procedure_id_procedure_id FOREIGN KEY (procedure_id)
  REFERENCES procedure(id)
);


--************************************** [training]

CREATE TABLE training
(
  id               SERIAL NOT NULL ,
  user_id          BIGINT NOT NULL ,
  dataset_code     VARCHAR(50) NOT NULL ,
  start_date       TIMESTAMP(6)  ,
  end_date         TIMESTAMP(6)  ,
  algorithm_id     BIGINT NOT NULL ,
  granularity_days INT NOT NULL ,
  anticipation     INT NOT NULL ,
  status           VARCHAR(50) NOT NULL ,

  CONSTRAINT PK_training PRIMARY KEY (id ),
  CONSTRAINT FK_training_algorithm_id_training_id FOREIGN KEY (algorithm_id)
  REFERENCES algorithm(id)
);



--************************************** [algorithm_parameters]

CREATE TABLE algorithm_parameter
(
  id            SERIAL NOT NULL ,
  algorithm_id  BIGINT NOT NULL ,
  name          VARCHAR(50) NOT NULL ,
  field_name    VARCHAR(50) NOT NULL ,
  default_value VARCHAR(50)  ,
  range         VARCHAR(50)  ,
  data_type     VARCHAR(50) NOT NULL ,

  CONSTRAINT PK_algorithm_parameter PRIMARY KEY (id ),
  CONSTRAINT FK_algorithm_parameter_algorithm_id_algorithm_id FOREIGN KEY (algorithm_id)
  REFERENCES algorithm(id)
);


--************************************** [training_variable]

CREATE TABLE training_variable
(
  id          SERIAL NOT NULL ,
  training_id BIGINT NOT NULL ,
  name        TEXT NOT NULL ,
  target      BOOLEAN,
  data_type     VARCHAR(50) NOT NULL ,

  CONSTRAINT PK_training_variable PRIMARY KEY (id ),
  CONSTRAINT FK_training_variable_training_id_training_id FOREIGN KEY (training_id)
  REFERENCES training(id)
);


--************************************** [validation_value]

CREATE TABLE validation_value
(
  id           SERIAL NOT NULL ,
  k            INT NOT NULL ,
  train        DOUBLE PRECISION NULL ,
  training_id  BIGINT NOT NULL ,
  procedure_id BIGINT NOT NULL ,

  CONSTRAINT PK_validation_value PRIMARY KEY (id ),
  CONSTRAINT FK_validation_value_training_id_training_id FOREIGN KEY (training_id)
  REFERENCES training(id),
  CONSTRAINT FK_validation_value_procedure_id_procedure_id FOREIGN KEY (procedure_id)
  REFERENCES procedure(id)
);


--************************************** [model]

CREATE TABLE model
(
  id          SERIAL NOT NULL ,
  model       JSONB NOT NULL ,
  training_id BIGINT NOT NULL ,

  CONSTRAINT PK_model PRIMARY KEY (id ),
  CONSTRAINT FK_model_training_id_training_id FOREIGN KEY (training_id)
  REFERENCES training(id)
);


--SKIP Index: [fkIdx_152]


--************************************** [algorithm_training_parameters]

CREATE TABLE algorithm_training_parameter
(
  id            SERIAL NOT NULL ,
  name          VARCHAR(50) NOT NULL ,
  field_name    VARCHAR(50) NOT NULL ,
  value         VARCHAR(50) NOT NULL ,
  training_id   BIGINT NOT NULL ,
  default_value VARCHAR(50)  ,
  range         VARCHAR(50)  ,
  data_type     VARCHAR(50) NOT NULL ,

  CONSTRAINT PK_algorithm_training_parameter PRIMARY KEY (id ),
  CONSTRAINT FK_algorithm_training_parameter_training_id_training_id FOREIGN KEY (training_id)
  REFERENCES training(id)
);


--************************************** [normalization_parameter_value]

CREATE TABLE normalization_parameter_value
(
  id                           SERIAL NOT NULL ,
  value                        NUMERIC(18,0) NOT NULL ,
  normalization_parameter_id   BIGINT NOT NULL ,
  training_variable_id BIGINT NOT NULL ,

  CONSTRAINT PK_normalization_parameter_value PRIMARY KEY (id ),
  CONSTRAINT FK_normalization_parameter_value_normalization_parameter_id_normalization_parameter_id FOREIGN KEY (normalization_parameter_id)
  REFERENCES normalization_parameter(id),
  CONSTRAINT FK_normalization_parameter_value_training_variable_id_training_variable_id FOREIGN KEY (training_variable_id)
  REFERENCES training_variable(id)
);


--************************************** [model_variable]

CREATE TABLE model_variable
(
  id                           SERIAL NOT NULL ,
  model_id                     BIGINT NOT NULL ,
  training_variable_id BIGINT NOT NULL ,

  CONSTRAINT PK_model_variable PRIMARY KEY (id ),
  CONSTRAINT FK_model_variable_model_id_model_id FOREIGN KEY (model_id)
  REFERENCES model(id),
  CONSTRAINT FK_model_variable_training_variable_id_training_variable_id FOREIGN KEY (training_variable_id)
  REFERENCES training_variable(id)
);


--************************************** [prediction_schedule]

CREATE TABLE prediction_schedule
(
  id          SERIAL NOT NULL ,
  user_id          BIGINT NOT NULL ,
  start_date  TIMESTAMP NOT NULL ,
  end_date    TIMESTAMP ,
  status      VARCHAR(50) NOT NULL ,
  period_days INT NOT NULL ,
  time        TIMESTAMP NOT NULL ,
  model_id    BIGINT NOT NULL ,

  CONSTRAINT PK_prediction_schedule PRIMARY KEY (id ),
  CONSTRAINT FK_prediction_schedule_model_id_model_id FOREIGN KEY (model_id)
  REFERENCES model(id)
);


--SKIP Index: [fkIdx_278]


--************************************** [prediction]

CREATE TABLE prediction
(
  id              SERIAL NOT NULL ,
  predicted_value VARCHAR(50) NOT NULL ,
  date            TIMESTAMP NOT NULL ,
  model_id        BIGINT NOT NULL ,
  for_date        TIMESTAMP(3) NOT NULL ,

  CONSTRAINT PK_prediction PRIMARY KEY (id ),
  CONSTRAINT FK_prediction_model_id_model_id FOREIGN KEY (model_id)
  REFERENCES model(id)
);



--************************************** [evaluation_value]

CREATE TABLE evaluation_value
(
  id           SERIAL NOT NULL ,
  value        VARCHAR(50) NOT NULL ,
  model_id     BIGINT NOT NULL ,
  procedure_id BIGINT NOT NULL ,

  CONSTRAINT PK_evaluation_value PRIMARY KEY (id ),
  CONSTRAINT FK_evaluation_value_model_id_model_id FOREIGN KEY (model_id)
  REFERENCES model(id),
  CONSTRAINT FK_evaluation_value_procedure_id_procedure_id FOREIGN KEY (procedure_id)
  REFERENCES procedure(id)
);


--************************************** [prediction_variable_value]

CREATE TABLE prediction_variable_value
(
  id                SERIAL NOT NULL ,
  value             VARCHAR(50) NOT NULL ,
  model_variable_id BIGINT NOT NULL ,
  prediction_id     BIGINT NOT NULL ,

  CONSTRAINT PK_prediction_variable_value PRIMARY KEY (id ),
  CONSTRAINT FK_prediction_variable_value_model_variable_id_model_variable_id FOREIGN KEY (model_variable_id)
  REFERENCES model_variable(id),
  CONSTRAINT FK_prediction_variable_value_prediction_id_prediction_id FOREIGN KEY (prediction_id)
  REFERENCES prediction(id)
);

