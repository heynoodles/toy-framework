package com.gaoxin.toy4j.framework.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 试图对象
 * @author gaoxin.wei
 */
public class View {

    /** 试图路径 **/
    private String path;

    /** 模型数据 **/
    private Map<String, Object> model;

    public View(String path) {
        this.path = path;
        this.model = new HashMap<String, Object>();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }
}
