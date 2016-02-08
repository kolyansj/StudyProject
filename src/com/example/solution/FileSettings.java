/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.solution;

import com.example.solution.model.provider.DataProvider;
import com.example.solution.model.provider.XMLProvider;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Nikolay
 */
public class FileSettings {
    
    //private static final Logger LOG = Logger.getLogger(FileSettings.class.getName());
    private static final FileSettings settings = new FileSettings();
    
    public static FileSettings getInstance() {
        return settings;
    }
    
    private final Properties properties;
    
    public FileSettings() {
        properties = new Properties();        
    }
    
    public void loadSettings(String path) {
        //LOG.log(Level.INFO, "loadSettings resource file path: " + path);
        if(path != null) {
            try (InputStream input = new FileInputStream(path);) {
                properties.load(input);
                //LOG.log(Level.DEBUG, "loadSettings loaded settings: " + properties);
            } catch (Exception ex) {
                //LOG.log(Level.ERROR, "Exception ", ex);
            }
        } else {
            //LOG.log(Level.ERROR, "path resource file == null");
        }
    }
    
    public DataProvider getProvider() {
        String prop = properties.getProperty("provider", "xml");
        if (prop.equalsIgnoreCase("xml")) {
            return new XMLProvider();
        }
        throw new UnsupportedOperationException();
    }
    
    public File getDataFile() {
        String prop = properties.getProperty("xml", "resources/data.xml");
        if(prop != null) {
            File alarm = new File(prop);
            return alarm;
        }   
        return null;
    }
}
