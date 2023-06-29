/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alext
 */
public class Configuration {

    private static final Properties config = new Properties();
    private static InputStream configInput = null;    

    public static String LoadConfig(String key) {
        String value = "";

        try {
            String directoryName = Configuration.class.getClassLoader().getResource("Configuration").getPath();            
            configInput = new FileInputStream(directoryName+"/config.properties");
            config.load(configInput);
             value = config.getProperty(key);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        }

        return value;
    }
}