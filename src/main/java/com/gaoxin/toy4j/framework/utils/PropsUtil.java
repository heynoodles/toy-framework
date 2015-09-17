package com.gaoxin.toy4j.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author gaoxin.wei
 */
public class PropsUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropsUtil.class);

    /**
     * 加载属性文件
     * @param fileName
     * @return
     */
    public static Properties loadProps(String fileName) {
        Properties props = null;
        InputStream is = null;

        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (is == null) {
                throw new FileNotFoundException(fileName + " file is not found");
            }
            props = new Properties();
            props.load(is);
        } catch (Exception e) {
            logger.error("log properties file failure", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    logger.error("close input stream failure", e);
                }
            }
        }

        return props;
    }

    public static String getString(Properties props, String key) {
        return getString(props, key, "");
    }

    private static String getString(Properties props, String key, String s) {
        String value = s;
        if (props.containsKey(key))
            value = props.getProperty(key);
        return value;
    }


}
