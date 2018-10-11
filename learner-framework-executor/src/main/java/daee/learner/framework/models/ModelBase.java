package daee.learner.framework.models;

import com.mashape.unirest.http.exceptions.UnirestException;
import daee.learner.framework.dto.ModelDTO;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

import static daee.learner.framework.helper.MapperHelper.getAndSaveJson;

/**
 * Clase base a extender para crear un Modelo de Predicción
 * @param <T>
 */
public abstract class ModelBase<T> {
    Logger logger = Logger.getLogger(Model.class);

    T bytesToSparkModel(ModelDTO modelDTO) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(modelDTO.getModel());
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            return (T) Class.forName(getClass().getName()).cast(in.readObject());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }
    }

    Dataset<String> getDataToPredict(SparkSession sparkSession, String dataSetCode, String dataSetUrl) throws IOException, UnirestException {

        return getAndSaveJson(dataSetCode, dataSetUrl, sparkSession);
    }
}
