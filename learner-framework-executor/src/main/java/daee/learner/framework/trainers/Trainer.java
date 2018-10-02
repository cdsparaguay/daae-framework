package daee.learner.framework.trainers;

import com.mashape.unirest.http.exceptions.UnirestException;
import daee.learner.framework.dto.ModelDTO;
import daee.learner.framework.dto.TrainerDTO;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.ml.Model;
import org.apache.spark.ml.classification.MultilayerPerceptronClassificationModel;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.sql.SparkSession;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Interfaz fase para la implementación de algoritmos de entrenamiento
 */
public interface Trainer {

    /**
     * Método principal a implementar. Debe realizar el entrenamiento dado los parámetros recibidos en un
     * {@link TrainerDTO} y retornar un {@link ModelDTO} entrenado listo para ser persistido.
     * @param sparkSession:
     *                    Sesión spark en la cual se están realizado las tareas de entrenamiento
     * @param trainerDTO:
     *                  Parámetros a utilizar por el algoritmo de entrenamiento
     * @return ModelDTO
     * @throws IOException
     */
    ModelDTO train(SparkSession sparkSession, TrainerDTO trainerDTO) throws IOException, UnirestException;

}
