package daee.learner.framework.helper;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapperHelper {

    public static int[] stringArrToIntArr(String[] s) {
        int[] result = new int[s.length];
        for (int i = 0; i < s.length; i++) {
            result[i] = Integer.parseInt(s[i]);
        }
        return result;
    }



    private static Dataset<String> readJsonFromUrl(String url, SparkSession spark) throws IOException, JSONException, UnirestException {
        HttpResponse<JsonNode> jsonResponse = Unirest.post(url)
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "b8f0eadd2612345745dc9206057c49821ce5a48c76ec488f69d20c7e69c90b36")
                .queryString("offset", 0)
                .queryString("count", 100000)
                .body("[]")
                .asJson();

        JSONArray array = jsonResponse.getBody().getArray();
        List<String> jsonData = new ArrayList<>();
        for(Object object: array) {

            String jsonObject = ((JSONObject) object).toString();
            jsonData.add(jsonObject);
        }
        return spark.createDataset(jsonData, Encoders.STRING());

    }

    public static Dataset<String> getAndSaveJson(String datasetcode, String url, SparkSession sparkSession) throws IOException, UnirestException {
        return readJsonFromUrl(url, sparkSession);
    }

}
