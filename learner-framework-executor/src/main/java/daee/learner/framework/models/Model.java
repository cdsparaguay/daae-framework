package daee.learner.framework.models;

import daee.learner.framework.dto.ModelDTO;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * Interfaz a implementar para crear un nuevo Modelo de Predicción
 */
public interface Model {

    /**
     * Método que recibe los metadatos del Modelo a implementar en {@link ModelDTO} y los datos
     * de entrada en un {@link Dataset<Row>} y retorna un {@link Dataset<Row>} con los resultados
     * de la predicción.
     * @param model
     * @return
     */
    Dataset<Row> predict(SparkSession session, ModelDTO model, String dataSetName, String dataSetUrl);
}
