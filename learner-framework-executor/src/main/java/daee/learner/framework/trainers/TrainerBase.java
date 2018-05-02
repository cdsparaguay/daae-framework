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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public abstract class TrainerBase<T> {

    Logger logger = Logger.getLogger(Trainer.class);

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

    ModelDTO sparkModelToDTO(Model model) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(model);
        oos.flush();
        oos.close();
        return new ModelDTO(baos.toByteArray(), getClass().getName());
    }

    private String getAndSaveJson(String datasetcode, URL url, SparkSession sparkSession) throws IOException {
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

        return new VectorAssembler()
                .setOutputCol("features")
                .setInputCols(trainerDTO.getFeatureVariablesName())
                .transform(data);

    }
}
