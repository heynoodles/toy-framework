package com.gaoxin.toy4j.framework.helper;

import java.util.Map;

/**
 * @author gaoxin.wei
 */
public final class IocHelper {

    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        // todo bean 之间的依赖关系
    }
}
