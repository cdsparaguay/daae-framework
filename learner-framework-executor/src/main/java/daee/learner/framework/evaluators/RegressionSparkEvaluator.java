package daee.learner.framework.evaluators;

import daee.learner.framework.dto.TrainingVariableDTO;
import org.apache.spark.ml.evaluation.RegressionEvaluator;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegressionSparkEvaluator implements Evaluator{
    @Override
    public Map<String, Object> evaluate(Dataset<Row> dataset, List<TrainingVariableDTO> variables) {
        RegressionEvaluator evaluator = new RegressionEvaluator()
                .setLabelCol("label")
                .setPredictionCol("prediction")
                .setMetricName("rmse");
        double rmse = evaluator.evaluate(dataset);
        Map<String, Object> aRet = new HashMap<>();
        aRet.put("rmse", rmse);
        return aRet;
    }
}
