package daee.learner.framework.trainers;

import daee.learner.framework.dto.ModelDTO;
import daee.learner.framework.dto.TrainerDTO;
import org.apache.spark.ml.Model;
import org.apache.spark.ml.classification.MultilayerPerceptronClassificationModel;
import org.apache.spark.sql.SparkSession;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public interface Trainer {

    ModelDTO train(SparkSession sparkSession, TrainerDTO trainerDTO) throws IOException;

    static ModelDTO sparkModelToDTO(Model model) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(model);
        oos.flush();
        oos.close();
        return new ModelDTO(baos.toByteArray(), MultilayerPerceptronClassificationModel.class.getName());
    }

}
