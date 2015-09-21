package com.gaoxin.toy4j.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;

/**
 * 编码与解码
 * @author gaoxin.wei
 */
public class CodecUtil {

    public static final Logger LOGGER = LoggerFactory.getLogger(CodecUtil.class);


    public static String decodeURL(String source) {
        String target;
        try {
            target = URLDecoder.decode(source, "UTF-8");
        } catch (Exception e) {
            LOGGER.error("decode url failure", e);
            throw new RuntimeException(e);
        }
        return target;
    }
}
