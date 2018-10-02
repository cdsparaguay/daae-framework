package daee.learner.framework.helper;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import daee.learner.framework.constants.Config;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MapperHelper {

    public static int[] stringArrToIntArr(String[] s) {
        int[] result = new int[s.length];
        for (int i = 0; i < s.length; i++) {
            result[i] = Integer.parseInt(s[i]);
        }
        return result;
    }



    private static JSONArray readJsonFromUrl(String url) throws IOException, JSONException, UnirestException {
        HttpResponse<JsonNode> jsonResponse = Unirest.post(url)
                .header("accept", "application/json")
                .header("Authorization", "b8f0eadd2612345745dc9206057c49821ce5a48c76ec488f69d20c7e69c90b36")
                .queryString("offset", 0)
                .queryString("count", 1000)
                .body("[]")
                .asJson();

        Logger.getLogger(MapperHelper.class.getName()).info("JSON DATA " + jsonResponse.getBody());
        return jsonResponse.getBody().getArray();

    }

    public static String getAndSaveJson(String datasetcode, String url, SparkSession sparkSession) throws IOException, UnirestException {
        List<String> listToSave = new ArrayList<>();
        String fileName = datasetcode + ".json";
        listToSave.add(MapperHelper.readJsonFromUrl(url).toString());
        Dataset<String> toSave = sparkSession.createDataset(listToSave, Encoders.STRING());
        toSave.write().format("json").mode("overwrite").save(Config.HDFS_PATH+fileName);
        return Config.HDFS_PATH+fileName;
    }

}
