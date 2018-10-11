package daee.learner.framework.constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final Properties properties = readPropertiesFile();
    private static final String CONFIG_FILE = "application.properties";
    public static final String DB_DRIVER = properties.getProperty("dbdriver");
    public static final String DB_URL =  properties.getProperty("dburl");
    public static final String DB_USER =  properties.getProperty("dbusername");
    public static final String DB_PASS =  properties.getProperty("dbpassword");
    public static final String HDFS_PATH =  properties.getProperty("hdfs-url");
    public static final String SERVICE_URL =  properties.getProperty("service-url");
    public static final String DATA_URL =  properties.getProperty("data-url");

    private static Properties readPropertiesFile()
    {

        InputStream in = Config.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
        if (in == null) {
            return null;
        }
        Properties props = new java.util.Properties();
        try {
            props.load(in);
        } catch (IOException e) {
            return null;
        }
        return props;
    }
}
