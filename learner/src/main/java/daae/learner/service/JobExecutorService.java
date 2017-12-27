package daae.learner.service;

import daee.example.ExampleMLPJob;
import org.apache.spark.launcher.SparkLauncher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JobExecutorService {

    private static final String JAVA_HOME = System.getProperty("java.home");
    private static final String SPARK_HOME = "/usr/local/spark";

    @Value("${framework-jar-path}")
    private String JAR_PATH;
    @Value("${master-url}")
    private String MASTER;

    public String addJob() {
        try {
            new SparkLauncher()
                    .setSparkHome(SPARK_HOME)
                    .setJavaHome(JAVA_HOME)
                    .setAppResource(JAR_PATH)
                    .addFile("ftp://dengue:d3ngu31@51.15.53.198/sample_multiclass_classification_data.txt")
                    .setMainClass(ExampleMLPJob.class.getName())
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
