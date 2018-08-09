package daae.learner.service;

import com.google.gson.Gson;
import daae.learner.models.Training;
import daee.learner.framework.JobHandler;
import daee.learner.framework.constants.JobType;
import daee.learner.framework.dto.ModelDTO;
import daee.learner.framework.dto.ParamDTO;
import daee.learner.framework.dto.TrainerDTO;
import daee.learner.framework.models.Model;
import daee.learner.framework.trainers.DecisionTreeTrainer;
import org.apache.log4j.Logger;
import org.apache.spark.launcher.SparkLauncher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class JobExecutorService {

    private static final String JAVA_HOME = System.getProperty("java.home");
    private static final String SPARK_HOME = "/usr/local/spark";

    private Logger logger = Logger.getLogger(Model.class);

    @Value("${framework-jar-path}")
    private String JAR_PATH;
    @Value("${master-url}")
    private String MASTER;

    public String train(TrainerDTO trainerDTO) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(trainerDTO);

        logger.info("Args params: " + JobType.TRAINING + trainerDTO.getAlgorithName() +  jsonString);

        try {
            new SparkLauncher()
                    .setSparkHome(SPARK_HOME)
                    .setJavaHome(JAVA_HOME)
                    .setAppResource(JAR_PATH)
                    .setMainClass(JobHandler.class.getName())
                    .addAppArgs(JobType.TRAINING, trainerDTO.getAlgorithName(), jsonString)
                    .setMaster(MASTER)
                    .setAppName("EXAMPLE")
                    .setDeployMode("cluster")
                    .startApplication();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String predict(ModelDTO modelDTO, String datasetName, String datasetUrl) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(modelDTO);
        try {
            new SparkLauncher()
                    .setSparkHome(SPARK_HOME)
                    .setJavaHome(JAVA_HOME)
                    .setAppResource(JAR_PATH)
                    .setMainClass(JobHandler.class.getName())
                    .addAppArgs(JobType.PREDICTION, modelDTO.getClass_name(), datasetName, datasetUrl, jsonString)
                    .setMaster(MASTER)
                    .setAppName("EXAMPLE")
                    .setDeployMode("cluster")
                    .startApplication();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
