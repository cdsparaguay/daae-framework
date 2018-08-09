package daee.learner.framework.helper;

import daee.learner.framework.constants.Config;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
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

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONArray readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is =  new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            return new JSONArray(jsonText);
        }
    }

    public static String getAndSaveJson(String datasetcode, String url, SparkSession sparkSession) throws IOException {
        List<String> listToSave = new ArrayList<>();
        String fileName = datasetcode + ".json";
        listToSave.add(MapperHelper.readJsonFromUrl(url).toString());
        Dataset<String> toSave = sparkSession.createDataset(listToSave, Encoders.STRING());
        toSave.write().format("json").mode("overwrite").save(Config.HDFS_PATH+fileName);
        return Config.HDFS_PATH+fileName;

    }

}
