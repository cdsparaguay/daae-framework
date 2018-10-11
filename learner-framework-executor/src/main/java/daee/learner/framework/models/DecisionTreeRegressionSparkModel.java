package daee.learner.framework.models;

import com.mashape.unirest.http.exceptions.UnirestException;
import daee.learner.framework.dto.ModelDTO;
import org.apache.spark.ml.regression.DecisionTreeRegressionModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;

public class DecisionTreeRegressionSparkModel extends ModelBase<DecisionTreeRegressionModel> implements Model {
    @Override
    public Dataset<Row> predict(SparkSession session, ModelDTO model, String dataSetName, String dataSetUrl) throws UnirestException {

        try {
            Dataset<String> data = getDataToPredict(session, dataSetName, dataSetUrl);
            DecisionTreeRegressionModel modelToPredict = bytesToSparkModel(model);
            return modelToPredict.transform(data);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
