package daee.learner.framework.trainers;

import com.mashape.unirest.http.exceptions.UnirestException;
import daee.learner.framework.dto.ModelDTO;
import daee.learner.framework.dto.TrainerDTO;
import daee.learner.framework.models.DecisionTreeRegressionSparkModel;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier;
import org.apache.spark.ml.evaluation.RegressionEvaluator;
import org.apache.spark.ml.regression.DecisionTreeRegressionModel;
import org.apache.spark.ml.regression.DecisionTreeRegressor;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import javax.management.MalformedObjectNameException;
import java.io.IOException;

public class DecisionTreeTrainer extends TrainerBase<MultilayerPerceptronClassifier>
        implements Trainer {
    @Override
    public ModelDTO train(SparkSession sparkSession, TrainerDTO trainerDTO) throws IOException, UnirestException {

        JavaSparkContext jsc = new JavaSparkContext(sparkSession.sparkContext());
        Dataset<Row> data = getData(sparkSession, trainerDTO);
        Dataset<Row>[] splits = data.randomSplit(new double[]{0.7, 0.3});
        Dataset<Row> trainingData = splits[0];
        Dataset<Row> testData = splits[1];
        DecisionTreeRegressor dt = new DecisionTreeRegressor()
                .setFeaturesCol(trainerDTO.getTargetVariablesName()[0]);
        DecisionTreeRegressionModel model = dt.train(trainingData);
        // Make predictions.
        Dataset<Row> predictions = model.transform(testData);
// Select (prediction, true label) and compute test error.
        RegressionEvaluator evaluator = new RegressionEvaluator()
                .setLabelCol("label")
                .setPredictionCol("prediction")
                .setMetricName("rmse");
        double rmse = evaluator.evaluate(predictions);
        logger.info("Root Mean Squared Error (RMSE) on test data = " + rmse);


        logger.info("Learned regression tree model:\n" + model.toDebugString());

        return sparkModelToDTO(model, DecisionTreeRegressionSparkModel.class.getName(),
                trainerDTO.getTraniningId(), trainerDTO.getVariables());
    }
}
