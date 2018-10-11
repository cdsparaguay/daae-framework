package daee.learner.framework;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
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
                Trainer trainer = (Trainer) Class.forName("daee.learner.framework.trainers."+algorithmName).getConstructor().newInstance();
                ModelDTO model = trainer.train(spark, trainerParams);
                model.setInitialDate(trainerParams.getInitialDate());
                saveModel(model);
                break;
            case JobType.PREDICTION:
                gson = new Gson();
                String modelName = args[1];
                String dataSetName = args[2];
                String dataSetUrl = args[3];
                ModelDTO modelParams = gson.fromJson(args[4] , ModelDTO.class);
                Model modelToPredict = (Model) Class.forName(modelName).getConstructor().newInstance();
                Dataset<Row> prediction = modelToPredict.predict(spark, modelParams, dataSetName, dataSetUrl);
                break;


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

        private static void saveModel(ModelDTO modelDTO) throws UnirestException {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss").create();

           // Gson gson = new Gson();
            logger.info("DATA MODEL GENERATED " + modelDTO.toString());
            HttpResponse<String> jsonResponse = Unirest.post(Config.SERVICE_URL+"models")
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(gson.toJson(modelDTO)).asString();
            logger.info(jsonResponse.getBody());
        }
}
