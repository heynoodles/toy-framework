package com.gaoxin.toy4j.framework.bean;

import java.lang.reflect.Method;

/**
 * 封装Action信息
 * @author gaoxin.wei
 */
public class Handler {

    /** Controller类 **/
    private Class<?> controllerClass;

    /** Action方法 **/
    private Method method;

    public Handler(Class<?> controllerClass, Method method) {
        this.controllerClass = controllerClass;
        this.method = method;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(Class<?> controllerClass) {
        this.controllerClass = controllerClass;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
