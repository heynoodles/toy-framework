package com.gaoxin.toy4j.framework;

import com.gaoxin.toy4j.framework.helper.BeanHelper;
import com.gaoxin.toy4j.framework.helper.ClassHelper;
import com.gaoxin.toy4j.framework.helper.ControllerHelper;
import com.gaoxin.toy4j.framework.helper.IocHelper;
import com.gaoxin.toy4j.framework.utils.ClassUtil;

/**
 * @author gaoxin.wei
 */
public final class HelperLoader {

    public static void init() {
        Class<?>[] classList = {
            ClassHelper.class,
            BeanHelper.class,
            IocHelper.class,
            ControllerHelper.class
        };
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName(), true);
        }
    }
}
