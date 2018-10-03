package daee.learner.framework.trainers;

import com.mashape.unirest.http.exceptions.UnirestException;
import daee.learner.framework.dto.ModelDTO;
import daee.learner.framework.dto.TrainerDTO;
import org.apache.spark.ml.regression.LinearRegression;
import org.apache.spark.ml.regression.LinearRegressionModel;
import org.apache.spark.ml.regression.LinearRegressionTrainingSummary;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;

public class LinearRegressionTrainer extends TrainerBase<LinearRegression> implements Trainer {


    /**
     * Params:
     *   .setMaxIter(10)
     * .setRegParam(0.3)
     * .setElasticNetParam(0.8);
     * @param sparkSession:
     *                    Sesión spark en la cual se están realizado las tareas de entrenamiento
     * @param trainerDTO:
     *                  Parámetros a utilizar por el algoritmo de entrenamiento
     * @return
     * @throws IOException
     */
    @Override
    public ModelDTO train(SparkSession sparkSession, TrainerDTO trainerDTO) throws IOException, UnirestException {

        Dataset<Row> data = getData(sparkSession, trainerDTO);
        LinearRegression lr = new LinearRegression();
        setValues(lr, trainerDTO.getParams());
        LinearRegressionModel lrModel = lr.fit(data);

        // Print the coefficients and intercept for linear regression.
        logger.info("Coefficients: "
                + lrModel.coefficients() + " Intercept: " + lrModel.intercept());
        // Summarize the model over the training set and print out some metrics.
        LinearRegressionTrainingSummary trainingSummary = lrModel.summary();
        logger.info("numIterations: " + trainingSummary.totalIterations());
        logger.info("objectiveHistory: " + Vectors.dense(trainingSummary.objectiveHistory()));
        trainingSummary.residuals().show();
        logger.info("RMSE: " + trainingSummary.rootMeanSquaredError());
        logger.info("r2: " + trainingSummary.r2());

        return sparkModelToDTO(lrModel, LinearRegressionModel.class.getName(), trainerDTO.getTrainingId(),
                trainerDTO.getVariables());

    }
}
