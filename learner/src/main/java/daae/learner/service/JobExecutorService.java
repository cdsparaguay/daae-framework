package daae.learner.service;

import com.google.gson.Gson;
import daee.learner.framework.JobHandler;
import daee.learner.framework.constants.JobType;
import daee.learner.framework.dto.ParamDTO;
import daee.learner.framework.dto.TrainerDTO;
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

    public String addJob() {
        TrainerDTO dto = new TrainerDTO();
        dto.setDataset("prueba");
        List<ParamDTO> params = new ArrayList<>();
        params.add(new ParamDTO("layers","4,5,4,3","ARRAY"));
        params.add(new ParamDTO("maxIter","100","INTEGER"));
        params.add(new ParamDTO("seed","1234","INTEGER"));
        params.add(new ParamDTO("blockSize","128","INTEGER"));
        dto.setParams(params);
        Gson gson = new Gson();
        String jsonString = gson.toJson(dto);

        try {
            new SparkLauncher()
                    .setSparkHome(SPARK_HOME)
                    .setJavaHome(JAVA_HOME)
                    .setAppResource(JAR_PATH)
                    .addFile("ftp://dengue:d3ngu31@51.15.53.198/sample_multiclass_classification_data.txt")
                    .setMainClass(JobHandler.class.getName())
                    .addAppArgs(JobType.TRAINING, "daee.learner.framework.trainers.MultilayerPerceptronClassifierTrainer", jsonString)
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
