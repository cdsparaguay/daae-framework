package daee.learner.framework.evaluators;

import daee.learner.framework.dto.TrainingVariableDTO;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.List;
import java.util.Map;

/**
 * Interfaz a implementar para agregar un algoritmo de evaluación de una predicción realizada
 */
public interface Evaluator {

    /**
     * Método que recibe un conjunto de predicciones realizadas y retorna su precisión según el algoritmo
     * implementado
     * @param dataset:
     *               conjunto de datos predichos y esperados
     *
     * @return
     */
    Map<String, Object> evaluate(Dataset<Row> dataset, String realName, String predictedName);

}
