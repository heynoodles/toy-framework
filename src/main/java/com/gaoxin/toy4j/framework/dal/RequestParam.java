package com.gaoxin.toy4j.framework.dal;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gaoxin.wei
 * 请求参数封装
 */
public class RequestParam {

    private String sql = "";

    private Map<String, Object> params = new HashMap<String, Object>();

    public Object get(String name) {
        return params.get(name);
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void add(String name, Object param) {
        params.put(name, param);
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
