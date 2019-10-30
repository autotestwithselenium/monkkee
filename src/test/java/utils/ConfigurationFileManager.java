package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationFileManager {

    private static ConfigurationFileManager instance;
    private static final Object lock = new Object();
    private static String propertyFilePath = "src/test/resources/configuration.properties";

    private static String login;
    private static String password;
    private static String urlBeforeLogin;
    private static String urlAfterLogin;


    public static ConfigurationFileManager getInstance() {
        if (instance == null) {
            synchronized (lock) {
                instance = new ConfigurationFileManager();
                instance.loadData();
            }
        }
        return instance;
    }

    private void loadData() {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(propertyFilePath));
        } catch (IOException e) {
            System.out.println("Configuration properties file cannot be found");
        }


        login = prop.getProperty("login");
        password = prop.getProperty("password");
        urlBeforeLogin = prop.getProperty("urlBeforeLogin");
        urlAfterLogin = prop.getProperty("urlAfterLogin");
    }


    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
    public String getUrlBeforeLogin() {
        return urlBeforeLogin;
    }
    public String getUrlAfterLogin() {
        return urlAfterLogin;
    }

}