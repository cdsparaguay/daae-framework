package daee.learner.framework.trainers;

import daee.learner.framework.dto.ModelDTO;
import daee.learner.framework.dto.ParamDTO;
import daee.learner.framework.dto.TrainerDTO;
import daee.learner.framework.helper.MapperHelper;
import org.apache.spark.SparkFiles;
import org.apache.spark.ml.classification.MultilayerPerceptronClassificationModel;
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;

public class MultilayerPerceptronClassifierTrainer extends TrainerBase<MultilayerPerceptronClassifier>
        implements Trainer {

    @Override
    public ModelDTO train(SparkSession sparkSession, TrainerDTO trainerDTO) throws IOException {

        logger.info("Training with algorithm "+ this.getClass().getName());
        String fileName = SparkFiles.get("sample_multiclass_classification_data.txt");
        Dataset<Row> dataFrame = sparkSession.read().format("libsvm").load(fileName);
        Dataset<Row>[] splits = dataFrame.randomSplit(new double[]{0.6, 0.4}, 1234L);
        Dataset<Row> train = splits[0];
        Dataset<Row> test = splits[1];
        MultilayerPerceptronClassifier trainer = new MultilayerPerceptronClassifier();
        setValues(trainer, trainerDTO.getParams());
        MultilayerPerceptronClassificationModel model = trainer.fit(train);
        return Trainer.sparkModelToDTO( model);
    }

    @Override
    protected void set(Object object, ParamDTO param) {
        if (param.getMethod().equals("layers")) {
            MultilayerPerceptronClassifier train = (MultilayerPerceptronClassifier) object;
            train.setLayers(MapperHelper.stringArrToIntArr(param.getValue().split(",")));
        } else {
            super.set(object, param);
        }
    }
}
