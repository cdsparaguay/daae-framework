package daee.learner.framework;

import com.google.gson.Gson;
import daee.learner.framework.constants.Config;
import daee.learner.framework.constants.JobType;
import daee.learner.framework.dto.ModelDTO;
import daee.learner.framework.dto.TrainerDTO;
import daee.learner.framework.models.Model;
import daee.learner.framework.trainers.Trainer;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Properties;

public class JobHandler {

    private static final Logger logger = Logger.getLogger(JobHandler.class);

    public static void main(String[] args) throws Exception {

        if (args.length < 3) {
            throw new Exception("3 params expected");
        }
        String type = args[0];
        SparkSession spark = SparkSession
                .builder()
                .appName("JobHandler")
                .getOrCreate();
        logger.info("Job type: " + type);
        Gson gson = new Gson();
        switch (type) {
            case JobType.TRAINING:
                TrainerDTO trainerParams = gson.fromJson(args[2] , TrainerDTO.class);
                String algorithmName = args[1];
                Trainer trainer = (Trainer) Class.forName(algorithmName).getConstructor().newInstance();
                ModelDTO model = trainer.train(spark, trainerParams);
                saveModel(model);
            case JobType.PREDICTION:
                gson = new Gson();
                String modelName = args[1];
                String dataSetName = args[2];
                String dataSetUrl = args[3];
                ModelDTO modelParams = gson.fromJson(args[4] , ModelDTO.class);
                Model modelToPredict = (Model) Class.forName(modelName).getConstructor().newInstance();
                Dataset<Row> prediction = modelToPredict.predict(spark, modelParams, dataSetName, dataSetUrl);


        }
    }

    private static void saveModel(ModelDTO modelDTO, SparkSession sparkSession) throws IOException {
        logger.info("Saving model " + modelDTO.getClass_name());
        JavaSparkContext jsc = new JavaSparkContext(sparkSession.sparkContext());
        JavaRDD rdd = jsc.parallelize(Collections.singletonList((modelDTO)));
        Dataset<Row> modelFrame = sparkSession.createDataFrame(rdd, daee.learner.framework.dto.ModelDTO.class);
        Properties p = new Properties();
        p.setProperty("user", Config.DB_USER);
        p.setProperty("password",Config.DB_PASS);
        modelFrame.write()
                .option("driver",Config.DB_DRIVER)
                .mode("append")
                .jdbc(Config.DB_URL,"public.test_model", p);

        }

        private static void saveModel(ModelDTO modelDTO) {
            URL url;
            try {
                url = new URL(Config.SERVICE_URL+"models/add");

                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(modelDTO.toString());
            out.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            logger.info("Save successful");
            } catch (IOException e) {
                logger.error(e);
            }
        }
}
