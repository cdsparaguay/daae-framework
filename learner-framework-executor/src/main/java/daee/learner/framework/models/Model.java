package daee.learner.framework.models;

import daee.learner.framework.dto.ModelDTO;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

/**
 * Interfaz a implementar para crear un nuevo Modelo de Predicción
 */
public interface Model {

    /**
     * Método que recibe los metadatos del Modelo a implementar en {@link ModelDTO} y los datos
     * de entrada en un {@link Dataset<Row>} y retorna un {@link Dataset<Row>} con los resultados
     * de la predicción.
     * @param model
     * @param data
     * @return
     */
    Dataset<Row> predict(ModelDTO model, Dataset<Row> data);
}
