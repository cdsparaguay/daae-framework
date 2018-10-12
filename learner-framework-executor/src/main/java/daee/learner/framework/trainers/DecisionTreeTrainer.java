package daee.learner.framework.trainers;

import com.mashape.unirest.http.exceptions.UnirestException;
import daee.learner.framework.dto.ModelDTO;
import daee.learner.framework.dto.Predicted;
import daee.learner.framework.dto.TrainerDTO;
import daee.learner.framework.evaluators.RegressionSparkEvaluator;
import daee.learner.framework.models.DecisionTreeRegressionSparkModel;
import org.apache.spark.ml.regression.DecisionTreeRegressionModel;
import org.apache.spark.ml.regression.DecisionTreeRegressor;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DecisionTreeTrainer extends TrainerBase<DecisionTreeRegressor>
        implements Trainer {
    @Override
    public ModelDTO train(SparkSession sparkSession, TrainerDTO trainerDTO) throws IOException, UnirestException,
            ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException {

        Dataset<Row> data = getData(sparkSession, trainerDTO);
        data = data.withColumnRenamed(trainerDTO.getTargetVariablesName()[0], "label");
        data = data.withColumn("label", functions.rint("label"));

        DecisionTreeRegressor dt = new DecisionTreeRegressor()
                .setFeaturesCol("features")
                .setPredictionCol(trainerDTO.getTargetVariablesName()[0]);
        setValues(dt, trainerDTO.getParams());
        DecisionTreeRegressionModel model = dt.train(data);
        // Make predictions.
        Dataset<Row> predictions = model.transform(data);
        List<Predicted> predicteds = new ArrayList<>();
        for(Row prediction : predictions.collectAsList()) {
            predicteds.add(new Predicted(String.valueOf(prediction.getDouble(prediction.fieldIndex("label"))),
                    String.valueOf(prediction.getDouble(prediction.fieldIndex("prediction")))));
        }
// Select (prediction, true label) and compute test error.

        RegressionSparkEvaluator evaluator = (RegressionSparkEvaluator)
                Class.forName("daee.learner.framework.evaluators."+trainerDTO.getEvaluatorName()).getConstructor().newInstance();
        Map<String, Object> rmse = evaluator.evaluate(predictions, "label", "prediction");
        logger.info("Root Mean Squared Error (RMSE) on test data = " + rmse.get("rmse"));


        logger.info("Learned regression tree model:\n" + model.toDebugString());

        return sparkModelToDTO(model, DecisionTreeRegressionSparkModel.class.getName(),
                trainerDTO.getTrainingId(), trainerDTO.getVariables(), rmse.get("rmse").toString(),
                trainerDTO.getEvaluatorName(), predicteds, trainerDTO.getInitialDate());
    }
}
