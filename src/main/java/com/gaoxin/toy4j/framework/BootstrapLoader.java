package com.gaoxin.toy4j.framework;

import com.gaoxin.toy4j.framework.core.*;

/**
 * 容器初始化
 * @author gaoxin.wei
 */
public class BootstrapLoader {

    public static void init() {
        Initialize[] initializes = new Initialize[]{
            ClassHolder.INSTANCE,
            BeanHolder.INSTANCE,
            AopInitializer.INSTANCE,
            RepositoryInitializer.INSTANCE,
            IocInitializer.INSTANCE,
            ControllerHolder.INSTANCE
        };

        for (Initialize initializer : initializes) {
            initializer.init();
        }
    }

}
