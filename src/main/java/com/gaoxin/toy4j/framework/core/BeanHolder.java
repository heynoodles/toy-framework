package com.gaoxin.toy4j.framework.core;

import com.gaoxin.toy4j.framework.utils.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author gaoxin.wei
 */
public class BeanHolder implements Initialize {

    private Map<Class<?>, Object> BEAN_MAP = new HashMap<Class<?>, Object>();

    private BeanHolder() {}

    public static BeanHolder INSTANCE = new BeanHolder();

    @Override
    public void init() {
        Set<Class<?>> beanClassSet = ClassHolder.INSTANCE.getBeanClassSet();
        for (Class<?> beanClass : beanClassSet) {
            Object obj = ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass, obj);
        }
    }

    public void setBean(Class<?> cls, Object o) {
        BEAN_MAP.put(cls, o);
    }

    public Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    public <T> T getBean(Class<T> cls) {
        return (T)BEAN_MAP.get(cls);
    }

}
