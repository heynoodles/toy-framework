package com.gaoxin.toy4j.framework.helper;

import com.gaoxin.toy4j.framework.annotation.Inject;
import com.gaoxin.toy4j.framework.utils.ReflectionUtil;
import org.apache.commons.lang.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author gaoxin.wei
 */
public final class IocHelper {

    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (beanMap != null && !beanMap.isEmpty()) {
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                Field[] fields = beanClass.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(fields)) {
                    for (Field field : fields) {
                        if (field.isAnnotationPresent(Inject.class)) {
                            Class<?> fieldClass = field.getType();
                            Object fieldInstance = beanMap.get(fieldClass);
                            if (fieldInstance != null) {
                                ReflectionUtil.setField(beanInstance, field, fieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
