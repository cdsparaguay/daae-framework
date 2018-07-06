package daee.learner.framework.trainers;

import daee.learner.framework.constants.Config;
import daee.learner.framework.constants.DataType;
import daee.learner.framework.dto.ModelDTO;
import daee.learner.framework.dto.ParamDTO;
import daee.learner.framework.dto.TrainerDTO;
import daee.learner.framework.helper.MapperHelper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.spark.ml.Model;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

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

    ModelDTO sparkModelToDTO(Model model, String className) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(model);
        oos.flush();
        oos.close();
        return new ModelDTO(baos.toByteArray(), className);
    }


    private String getAndSaveJson(String datasetcode, String url, SparkSession sparkSession) throws IOException {
        List<String> listToSave = new ArrayList<>();
        String fileName = datasetcode + ".json";
        listToSave.add(MapperHelper.readJsonFromUrl(url).toString());
        Dataset<String> toSave = sparkSession.createDataset(listToSave, Encoders.STRING());
        toSave.write().format("json").mode("overwrite").save(Config.HDFS_PATH+fileName);
        return Config.HDFS_PATH+fileName;

    }

    Dataset<Row> getData(SparkSession sparkSession, TrainerDTO trainerDTO) throws IOException {

        String fileName = getAndSaveJson(trainerDTO.getDataset(), trainerDTO.getDataUrl(), sparkSession);
        Dataset<Row> data = sparkSession.read().json(fileName);
        data.persist();
        data = data.select("value");
        for(String schem: data.schema().fieldNames()) {
            logger.info("FIELD: " + schem);
        }
        return new VectorAssembler()
                .setOutputCol("features")
                .setInputCols(trainerDTO.getFeatureVariablesName())
                .transform(data);

    }
}
