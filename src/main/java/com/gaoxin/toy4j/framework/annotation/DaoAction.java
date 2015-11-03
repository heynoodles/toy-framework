package com.gaoxin.toy4j.framework.annotation;

import com.gaoxin.toy4j.framework.dal.DaoActionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gaoxin.wei
 */
@Target(value = { ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DaoAction {

    DaoActionType action() default DaoActionType.QUERY;

}
