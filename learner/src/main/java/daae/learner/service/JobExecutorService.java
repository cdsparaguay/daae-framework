package daae.learner.service;

import com.google.gson.Gson;
import daae.learner.models.Training;
import daee.learner.framework.JobHandler;
import daee.learner.framework.constants.JobType;
import daee.learner.framework.dto.ParamDTO;
import daee.learner.framework.dto.TrainerDTO;
import daee.learner.framework.trainers.DecisionTreeTrainer;
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

    @Value("${framework-jar-path}")
    private String JAR_PATH;
    @Value("${master-url}")
    private String MASTER;

    public String addJob(TrainerDTO trainerDTO) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(trainerDTO);

        try {
            new SparkLauncher()
                    .setSparkHome(SPARK_HOME)
                    .setJavaHome(JAVA_HOME)
                    .setAppResource(JAR_PATH)
                    .setMainClass(JobHandler.class.getName())
                    .addAppArgs(JobType.TRAINING, DecisionTreeTrainer.class.getName(), jsonString)
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
