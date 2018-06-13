package com.ouisncf.inno.agora.propositionengine.poll;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class PollProperties {

  private static final String SEPARATOR = ";";

  private static Properties properties;

  private static void loadProperties(){
    try {
      InputStream input = ClassLoader.getSystemResourceAsStream("poll.properties");
      properties = new Properties();
      properties.load(new InputStreamReader(input, Charset.forName("UTF-8")));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static List<String> getDestinations(){
    loadProperties();
    return Arrays.asList(properties.getProperty("destination").split(SEPARATOR));
  }

  public static int getNbrFridays(){
    loadProperties();
    return Integer.parseInt(properties.getProperty("nbr_friday"));
  }

  public static String getPatternDateFriday(){
    loadProperties();
    return properties.getProperty("pattern_friday");
  }

  public static List<String> getPrices(){
    loadProperties();
    return Arrays.asList(properties.getProperty("price").split(SEPARATOR));
  }
  



}
