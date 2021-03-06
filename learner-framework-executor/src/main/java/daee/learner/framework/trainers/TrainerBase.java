package daee.learner.framework.trainers;

import com.mashape.unirest.http.exceptions.UnirestException;
import daee.learner.framework.constants.Config;
import daee.learner.framework.constants.DataType;
import daee.learner.framework.dto.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.spark.ml.Model;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import static daee.learner.framework.helper.MapperHelper.getAndSaveJson;

/**
 * Clase Base a extender para crear un nuevo algoritmo de Entrenamiento.
 * Posee implementaciones útiles a utilizar al realizar un entrenamiento.
 * @param <T>
 */
public abstract class TrainerBase<T> {

    Logger logger = Logger.getLogger(Trainer.class);

    /**
     * Método utilizado para setear al algoritmo a utilizar para el entramiento
     * los hiperparḿetros correspondientes, utilizando Reflection. Los nombres
     * de atributos se deben corresponder con los nombres de los atributos de la
     * clase a la que se le seterán los valores de hiperparámetros.
     * @param object:
     *              Objeto que representa el algoritmo de entrenamiento al que se le quieren setear los valores de
     *              parámetros
     * @param attributes:
     *                  Los atributos con los valores para setear los parámetros.
     */
    void setValues(T object, List<ParamDTO> attributes) {
        for(ParamDTO param: attributes) {
            set(object, param);
        }
    }

    protected void set(Object object, ParamDTO param) {
        try {
            BeanUtils.setProperty(object,param.getMethod(),preSet(param));
        } catch (InvocationTargetException | IllegalAccessException e) {
            logger.error(e.getCause());
        }
    }

    private Object preSet(ParamDTO param) {
        switch (param.getDataType()){
            case DataType.INTEGER:
                return Integer.valueOf(param.getValue());
            case DataType.DECIMAL:
                return Double.valueOf(param.getValue());
            default:
                return param.getValue();
        }
    }

    ModelDTO sparkModelToDTO(Model model, String className, Long training_id, List<TrainingVariableDTO> variables,
                             String evaluationResult, String evaluationName, List<Predicted> predicteds,
                             Date initialDate) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(model);
        oos.flush();
        oos.close();
        return new ModelDTO(baos.toByteArray(), className, training_id, variables, evaluationResult,
                evaluationName, predicteds, initialDate);
    }

    Dataset<Row> getData(SparkSession sparkSession, TrainerDTO trainerDTO) throws IOException, UnirestException {


        trainerDTO.setDataUrl(Config.DATA_URL + trainerDTO.getDataset());
        Dataset<String> data1 = getAndSaveJson(trainerDTO.getDataset(), trainerDTO.getDataUrl(), sparkSession);
        Dataset<Row> data = sparkSession.read().json(data1);
        data.persist();
        data.cache();
        logger.info("DATA " + data.head());
        logger.info("COUNT  " + data.count());
        for(String field: data.columns()) {
            logger.info("Field " + field);
        }

        for(String name: trainerDTO.getFeatureVariablesName()) {
            data = data.withColumn(name, functions.rint(name));
        }

        return new VectorAssembler()
                .setOutputCol("features")
                .setInputCols(trainerDTO.getFeatureVariablesName())
                .transform(data);

    }
}
