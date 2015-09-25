package com.gaoxin.toy4j.framework.annotation;

import java.lang.annotation.*;

/**
 * @author gaoxin.wei
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    Class<? extends Annotation> value();
}
