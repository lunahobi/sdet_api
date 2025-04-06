package helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyProvider {
    private static PropertyProvider instance;

    private final Properties properties = new Properties();

    public static synchronized PropertyProvider getInstance() {
        if (instance == null) {
            instance = new PropertyProvider();
        }
        return instance;
    }

    private PropertyProvider() {
        try (InputStream propertiesInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(System.getProperty("propFile", "env_local") + ".properties")) {
            properties.load(propertiesInputStream);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

}
