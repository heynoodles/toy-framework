package com.gaoxin.toy4j.framework.bean;

import java.util.Map;

/**
 * 请求参数对象
 * @author gaoxin.wei
 */
public class Param {

    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }
}
