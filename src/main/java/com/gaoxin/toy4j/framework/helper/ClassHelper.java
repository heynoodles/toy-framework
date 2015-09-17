package com.gaoxin.toy4j.framework.helper;

import com.gaoxin.toy4j.framework.utils.ClassUtil;

import java.util.Set;

/**
 * @author gaoxin.wei
 */
public class ClassHelper {

    /** 包下所有的类 **/
    private static final Set<Class<?>> CLASS_SET;

    static {
        String appBasePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(appBasePackage);
    }

    /**
     * 获取包下所有的类
     * @return
     */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * 获取所有的Service
     * @return
     */
    public static Set<Class<?>> getServiceClassSet() {
        // TODO: 15-9-16
        return null;
    }

    /**
     * 获取所有的Controller
     * @return
     */
    public static Set<Class<?>> getControllerClassSet() {
        // TODO: 15-9-16
        return null;
    }

    /**
     * 获取所有的Bean
     * @return
     */
    public static Set<Class<?>> getBeanClassSet() {
        // TODO: 15-9-16
        return null;
    }
}
