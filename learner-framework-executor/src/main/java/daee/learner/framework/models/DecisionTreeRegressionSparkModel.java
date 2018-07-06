package daee.learner.framework.models;

import daee.learner.framework.dto.ModelDTO;
import org.apache.spark.ml.regression.DecisionTreeRegressionModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.io.IOException;

public class DecisionTreeRegressionSparkModel extends ModelBase<DecisionTreeRegressionModel> implements Model {
    @Override
    public Dataset<Row> predict(ModelDTO model, Dataset<Row> data) {

        try {
            DecisionTreeRegressionModel modelToPredict = bytesToSparkModel(model);
            return modelToPredict.transform(data);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
