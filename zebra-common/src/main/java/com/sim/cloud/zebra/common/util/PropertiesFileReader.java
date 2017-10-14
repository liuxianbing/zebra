package com.sim.cloud.zebra.common.util;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 * Properties公用类
 *
 */
public class PropertiesFileReader {
  private static String prePath = "conf/";
  protected static final Log logger = LogFactory.getLog(PropertiesFileReader.class);
  private static final Lock lock = new ReentrantLock();
  private static final Map<String, Properties> PROP_CACHE =
      new ConcurrentHashMap<String, Properties>();

  /**
   * @param propertiesFilePath properties文件路径（名） 默认在resources/conf 下
   * @param key key值
   * @return val值
   */
  public static String getPropertiesVal(String propertiesFilePath, String key) {
    Properties p = getProperties(propertiesFilePath);
    String retVal = p.get(key) == null ? null : (String) p.get(key);
    return retVal;
  }

  public static String getPropertiesValNoCache(String propertiesFilePath, String key) {
    Properties p = getPropertiesNoCache(propertiesFilePath);
    String retVal = p.get(key) == null ? null : (String) p.get(key);
    return retVal;
  }

  /**
   *
   * @param propertiesFilePath
   * @return
   */
  public static Properties getProperties(String propertiesFilePath) {
    if (!PROP_CACHE.containsKey(propertiesFilePath)) {
      lock.lock();
      try {
        if (!PROP_CACHE.containsKey(propertiesFilePath)) {
          Properties p = new Properties();
          String propertiesRealFilePath = Thread.currentThread().getContextClassLoader()
              .getResource(prePath + propertiesFilePath).toURI().getPath();
          InputStreamReader isr =
              new InputStreamReader(new FileInputStream(propertiesRealFilePath), "UTF-8");
          BufferedReader in = new BufferedReader(isr);
          p.load(in);
          PROP_CACHE.put(propertiesFilePath, p);
        }
      } catch (Exception e) {
        logger.debug("文件[" + propertiesFilePath + "]读取异常", e);
      } finally {
        lock.unlock();
      }

    }
    return PROP_CACHE.get(propertiesFilePath);
  }

  public static Properties getPropertiesNoCache(String propertiesFilePath) {
    Properties p = new Properties();
    try {
      String propertiesRealFilePath = Thread.currentThread().getContextClassLoader()
          .getResource(prePath + propertiesFilePath).toURI().getPath();
      InputStreamReader isr =
          new InputStreamReader(new FileInputStream(propertiesRealFilePath), "UTF-8");
      BufferedReader in = new BufferedReader(isr);
      p.load(in);
    } catch (Exception e) {
      logger.debug("文件[" + propertiesFilePath + "]读取异常", e);
    }
    return p;
  }

  /**
   * 获取所有key值，以字符串集合的形式返回
   *
   * @param properties
   * @return
   */
  public static List<String> getKeys(Properties properties) {
    List<String> retLst = new ArrayList<String>();
    Enumeration<?> enu = properties.propertyNames();
    while (enu.hasMoreElements()) {
      Object key = enu.nextElement();
      retLst.add(key + "");
    }
    return retLst;
  }

  /**
   * 获取所有value值，以字符串集合的形式返回
   *
   * @param properties
   * @return
   */
  public static List<String> getValues(Properties properties) {
    List<String> retLst = new ArrayList<String>();
    Enumeration<?> enu = properties.elements();
    while (enu.hasMoreElements()) {
      Object key = enu.nextElement();
      retLst.add(key + "");
    }
    return retLst;
  }

  public static void main(String[] args) throws IOException {
    String s1 = getPropertiesVal("smsTemplate", "smsKey");
    System.out.println(s1);
  }
}
