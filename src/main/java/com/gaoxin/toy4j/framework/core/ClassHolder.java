package com.gaoxin.toy4j.framework.core;

import com.gaoxin.toy4j.framework.annotation.Controller;
import com.gaoxin.toy4j.framework.annotation.Repository;
import com.gaoxin.toy4j.framework.annotation.Service;
import com.gaoxin.toy4j.framework.helper.ConfigHelper;
import com.gaoxin.toy4j.framework.utils.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * @author gaoxin.wei
 */
public class ClassHolder implements Initialize {

    /** 包下所有的类 **/
    private Set<Class<?>> CLASS_SET;

    private ClassHolder() {}

    public static ClassHolder INSTANCE = new ClassHolder();

    @Override
    public void init() {
        String appBasePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(appBasePackage);
    }

    /**
     * 获取包下所有的类
     * @return
     */
    public Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * 获取所有的Service
     * @return
     */
    public Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Service.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取所有的Repository
     * @return
     */
    public Set<Class<?>> getRepositoryClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET) {
           if (cls.isAnnotationPresent(Repository.class)) {
               classSet.add(cls);
           }
        }
        return classSet;
    }

    /**
     * 获取所有的Controller
     * @return
     */
    public Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Controller.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取所有的Bean
     * @return
     */
    public Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        classSet.addAll(getServiceClassSet());
        classSet.addAll(getControllerClassSet());
        return classSet;
    }

    public Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotation) {
        Set<Class<?>> result = new HashSet<Class<?>>();
        for (Class<?> aClass : CLASS_SET) {
            if (aClass.isAnnotationPresent(annotation)) {
                result.add(aClass);
            }
        }
        return result;
    }

    public Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
        Set<Class<?>> result = new HashSet<Class<?>>();
        for (Class<?> aClass : CLASS_SET) {
            if (superClass.isAssignableFrom(aClass) && !superClass.equals(aClass)) {
                result.add(aClass);
            }
        }
        return result;
    }


}
